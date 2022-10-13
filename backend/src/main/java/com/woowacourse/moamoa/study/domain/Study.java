package com.woowacourse.moamoa.study.domain;

import static com.woowacourse.moamoa.study.domain.StudyStatus.IN_PROGRESS;
import static com.woowacourse.moamoa.study.domain.StudyStatus.PREPARE;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.study.domain.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import com.woowacourse.moamoa.study.service.exception.FailureKickOutException;
import com.woowacourse.moamoa.study.service.exception.FailureParticipationException;
import com.woowacourse.moamoa.study.service.exception.InvalidUpdatingException;
import com.woowacourse.moamoa.study.service.exception.OwnerCanNotLeaveException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Study {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private Content content;

    @Embedded
    private Participants participants;

    @Embedded
    private RecruitPlanner recruitPlanner;

    @Embedded
    private StudyPlanner studyPlanner;

    @Embedded
    private AttachedTags attachedTags;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Study(
            final Content content, final Participants participants, final AttachedTags attachedTags,
            final LocalDateTime createdAt, final Integer maxMemberCount, final LocalDate enrollmentEndDate,
            final LocalDate startDate, final LocalDate endDate
    ) {
        this(null, content, participants, attachedTags, createdAt,
                maxMemberCount, enrollmentEndDate, startDate, endDate);
    }

    public Study(
            final Long id,
            final Content content, final Participants participants, final AttachedTags attachedTags,
            final LocalDateTime createdAt, final Integer maxMemberCount, final LocalDate enrollmentEndDate,
            final LocalDate startDate, final LocalDate endDate

    ) {
        recruitPlanner = new RecruitPlanner(maxMemberCount, RecruitStatus.RECRUITMENT_START, enrollmentEndDate);
        studyPlanner = makeStudyPlanner(createdAt, startDate, endDate);

        if (isRecruitingAfterEndStudy(recruitPlanner, studyPlanner) ||
                isRecruitedOrStartStudyBeforeCreatedAt(recruitPlanner, studyPlanner, createdAt)) {
            throw new InvalidPeriodException();
        }

        if (studyPlanner.isInappropriateCondition(createdAt.toLocalDate())) {
            throw new InvalidPeriodException();
        }

        this.id = id;
        this.content = content;
        this.participants = participants;
        this.createdAt = createdAt;
        this.attachedTags = attachedTags;
        updatePlanners(createdAt.toLocalDate(), maxMemberCount, enrollmentEndDate, startDate, endDate);
    }

    private StudyPlanner makeStudyPlanner(final LocalDateTime createdAt,
                                         final LocalDate startDate,
                                         final LocalDate endDate) {
        if (startDate.equals(createdAt.toLocalDate())) {
            return new StudyPlanner(startDate, endDate, IN_PROGRESS);
        }
        return new StudyPlanner(startDate, endDate, PREPARE);
    }

    public boolean isParticipant(final Long memberId) {
        return participants.isParticipation(memberId);
    }

    public void participate(final Long memberId) {
        if (recruitPlanner.isCloseEnrollment()) {
            throw new FailureParticipationException();
        }

        final Participant participant = new Participant(memberId);
        participants.participate(participant.getMemberId());

        if (isFullOfCapacity()) {
            recruitPlanner.closeRecruiting();
        }
    }

    public void changeStatus(final LocalDate now) {
        recruitPlanner.updateRecruiting(now);
        studyPlanner.updateStatus(now);
    }

    public void kickOut(final Long ownerId, final Participant participant, final LocalDate now) {
        if (!participants.isOwner(ownerId)) {
            throw new FailureKickOutException();
        }

        leave(participant, now);
    }

    public void leave(final Participant participant, final LocalDate now) {
        verifyCanLeave(participant);
        participants.leave(participant);

        if (!recruitPlanner.isRecruitedBeforeThan(now)) {
            recruitPlanner.startRecruiting();
        }
    }

    private boolean isRecruitingAfterEndStudy(final RecruitPlanner recruitPlanner, final StudyPlanner studyPlanner) {
        return recruitPlanner.hasEnrollmentEndDate() && studyPlanner
                .isEndBeforeThan(recruitPlanner.getEnrollmentEndDate());
    }

    private boolean isRecruitedOrStartStudyBeforeCreatedAt(
            final RecruitPlanner recruitPlanner, final StudyPlanner studyPlanner, final LocalDateTime createdAt
    ) {
        return studyPlanner.isStartBeforeThan(createdAt.toLocalDate()) ||
                recruitPlanner.isRecruitedBeforeThan(createdAt.toLocalDate());
    }

    private void verifyCanLeave(final Participant participant) {
        if (participants.isOwner(participant.getMemberId())) {
            throw new OwnerCanNotLeaveException();
        }
        if (!participants.isParticipation(participant.getMemberId())) {
            throw new NotParticipatedMemberException();
        }
    }

    public boolean isProgressStatus() {
        return studyPlanner.isProgress();
    }

    public boolean isCloseStudy() {
        return studyPlanner.isCloseStudy();
    }

    private boolean isFullOfCapacity() {
        return recruitPlanner.hasCapacity() && recruitPlanner.getCapacity() == participants.getSize();
    }

    public MemberRole getRole(final Long memberId) {
        if (participants.isOwner(memberId)) {
            return MemberRole.OWNER;
        }
        if (participants.isParticipation(memberId)) {
            return MemberRole.MEMBER;
        }
        return MemberRole.NON_MEMBER;
    }

    public void updatePlanners(final LocalDate requestNow,
                               final Integer maxMemberCount, final LocalDate enrollmentEndDate,
                               final LocalDate startDate, final LocalDate endDate) {
        recruitPlanner = new RecruitPlanner(maxMemberCount, RecruitStatus.RECRUITMENT_START, enrollmentEndDate);
        studyPlanner = makeStudyPlanner(createdAt, startDate, endDate);

        validatePlanner(recruitPlanner, studyPlanner);

        updateStudyPlanner(studyPlanner, requestNow);

        if (recruitPlanner.getEnrollmentEndDate() == null && isNotMaxMemberCount(recruitPlanner)) {
            recruitPlanner.startRecruiting();
            setPlanner(studyPlanner, recruitPlanner);
            return;
        }

        if (recruitPlanner.isRecruitedBeforeThan(requestNow)) {
            recruitPlanner.closeRecruiting();
            setPlanner(studyPlanner, recruitPlanner);
            return;
        }

        if (isNotMaxMemberCount(recruitPlanner)) {
            recruitPlanner.startRecruiting();
            setPlanner(studyPlanner, recruitPlanner);
            return;
        }

        if (participants.isParticipantsMaxCount(recruitPlanner.getMaxMemberCount())) {
            recruitPlanner.closeRecruiting();
            setPlanner(studyPlanner, recruitPlanner);
            return;
        }

        throw new RuntimeException("스터디 모집 상태에서 오류가 발생했습니다.");
    }

    private boolean isNotMaxMemberCount(final RecruitPlanner recruitPlanner) {
        return recruitPlanner.getMaxMemberCount() == null || !participants.isParticipantsMaxCount(
                recruitPlanner.getMaxMemberCount());
    }

    private void validatePlanner(final RecruitPlanner recruitPlanner, final StudyPlanner studyPlanner) {
        if (isRecruitingAfterEndStudy(recruitPlanner, studyPlanner) ||
                isRecruitedOrStartStudyBeforeCreatedAt(recruitPlanner, studyPlanner, createdAt)) {
            throw new InvalidUpdatingException();
        }

        if (studyPlanner.isInappropriateCondition(createdAt.toLocalDate())) {
            throw new InvalidUpdatingException();
        }

        if ((recruitPlanner.getMaxMemberCount() != null && recruitPlanner.getMaxMemberCount() < participants.getSize())) {
            throw new InvalidUpdatingException();
        }
    }

    private void updateStudyPlanner(final StudyPlanner studyPlanner, final LocalDate requestNow) {
        if (studyPlanner.getStartDate().isAfter(requestNow)) {
            studyPlanner.prepareStudy();
        }

        if (studyPlanner.getStartDate().equals(requestNow) || studyPlanner.getStartDate().isBefore(
                requestNow)) {
            studyPlanner.inProgressStudy();
        }

        if (studyPlanner.getEndDate() != null && requestNow.isAfter(studyPlanner.getEndDate())) {
            studyPlanner.doneStudy();
        }
    }

    private void setPlanner(final StudyPlanner studyPlanner, final RecruitPlanner recruitPlanner) {
        this.studyPlanner = studyPlanner;
        this.recruitPlanner = recruitPlanner;
    }

    public void updateContent(Long memberId, Content content, AttachedTags attachedTags) {
        if (!participants.isOwner(memberId)) {
            throw new UnauthorizedException("스터디 방장만이 스터디를 수정할 수 있습니다.");
        }

        this.content = content;
        this.attachedTags = attachedTags;
    }
}
