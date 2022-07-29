package com.woowacourse.moamoa.study.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import com.woowacourse.moamoa.study.service.exception.FailureParticipationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
public class Study {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private Details details;

    @Embedded
    private Participants participants;

    @Embedded
    private RecruitPlanner recruitPlanner;

    @Embedded
    private AttachedTags attachedTags;

    @Embedded
    private Period period;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Study(final Details details, final Participants participants, final RecruitPlanner recruitPlanner,
                 final Period period, final AttachedTags attachedTags, LocalDateTime createdAt
    ) {
        this(null, details, participants, recruitPlanner, period, attachedTags, createdAt);
    }

    private Study(final Long id, final Details details, final Participants participants,
                  final RecruitPlanner recruitPlanner,
                  final Period period, final AttachedTags attachedTags, final LocalDateTime createdAt
    ) {
        if (isRecruitingBeforeStartStudy(recruitPlanner, period) ||
                isRecruitingOrStartStudyBeforeCreatedAt(recruitPlanner, period, createdAt)) {
            throw new InvalidPeriodException();
        }

        this.id = id;
        this.details = details;
        this.participants = participants;
        this.recruitPlanner = recruitPlanner;
        this.period = period;
        this.createdAt = createdAt;
        this.attachedTags = attachedTags;
    }

    private boolean isRecruitingBeforeStartStudy(final RecruitPlanner recruitPlanner, final Period period) {
        return recruitPlanner.hasEnrollmentEndDate() && period.isEndBeforeThan(recruitPlanner.getEnrollmentEndDate());
    }

    private boolean isRecruitingOrStartStudyBeforeCreatedAt(final RecruitPlanner recruitPlanner, final Period period,
                                                            final LocalDateTime createdAt) {
        return period.isStartBeforeThan(createdAt.toLocalDate()) ||
                recruitPlanner.isRecruitingBeforeThan(createdAt.toLocalDate());
    }

    public boolean isWritableReviews(final Long memberId) {
        return participants.isAlreadyParticipated(memberId) && !details.isPreparingStudy();
    }

    public void participate(final Long memberId) {
        if (recruitPlanner.isCloseEnrollment() || participants.isNotParticipable(memberId)) {
            throw new FailureParticipationException();
        }

        participants.participate(new Participant(memberId));

        if (participants.isFullOfMember()) {
            recruitPlanner.closeRecruiting();
        }
    }

    public boolean isNeedToCloseRecruiting(LocalDate now) {
        return recruitPlanner.isNeedToCloseRecruiting(now);
    }

    public void closeEnrollment() {
        recruitPlanner.closeRecruiting();
    }

    public boolean isCloseEnrollment() {
        return recruitPlanner.isCloseEnrollment();
    }
}
