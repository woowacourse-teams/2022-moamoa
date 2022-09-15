package com.woowacourse.moamoa.study.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.study.domain.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
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
            final Content content, final Participants participants, final RecruitPlanner recruitPlanner,
            final StudyPlanner studyPlanner, final AttachedTags attachedTags, LocalDateTime createdAt
    ) {
        this(null, content, participants, recruitPlanner, studyPlanner, attachedTags, createdAt);
    }

    public Study(
            final Long id, final Content content, final Participants participants,
            final RecruitPlanner recruitPlanner, final StudyPlanner studyPlanner, final AttachedTags attachedTags,
            final LocalDateTime createdAt
    ) {
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
        this.recruitPlanner = recruitPlanner;
        this.studyPlanner = studyPlanner;
        this.createdAt = createdAt;
        this.attachedTags = attachedTags;
    }

    public boolean isReviewWritable(final Long memberId) {
        return participants.isParticipation(memberId) && !studyPlanner.isPreparing();
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

    public void leave(final Participant participant) {
        verifyCanLeave(participant);
        participants.leave(participant);
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

    public void update(Long memberId, Content content, RecruitPlanner recruitPlanner, AttachedTags attachedTags,
                       StudyPlanner studyPlanner
    ) {
        if (isRecruitingAfterEndStudy(recruitPlanner, studyPlanner) ||
                isRecruitedOrStartStudyBeforeCreatedAt(recruitPlanner, studyPlanner, createdAt)) {
            throw new InvalidUpdatingException();
        }

        if (studyPlanner.isInappropriateCondition(createdAt.toLocalDate())) {
            throw new InvalidUpdatingException();
        }

        if ((recruitPlanner.getMax() != null && recruitPlanner.getMax() < participants.getSize())) {
            throw new InvalidUpdatingException();
        }

        checkOwner(memberId);
        this.content = content;
        this.recruitPlanner = recruitPlanner;
        this.attachedTags = attachedTags;
        this.studyPlanner = studyPlanner;
    }

    private void checkOwner(Long memberId) {
        if (!participants.isOwner(memberId)) {
            throw new UnauthorizedException("스터디 방장만이 스터디를 수정할 수 있습니다.");
        }
    }
}
