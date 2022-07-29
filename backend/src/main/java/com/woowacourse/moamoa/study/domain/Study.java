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
    private RecruitPlan recruitPlan;

    @Embedded
    private AttachedTags attachedTags;

    @Embedded
    private Period period;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Study(final Details details, final Participants participants, final RecruitPlan recruitPlan,
                 final Period period, final AttachedTags attachedTags, LocalDateTime createdAt
    ) {
        this(null, details, participants, recruitPlan, period, attachedTags, createdAt);
    }

    private Study(final Long id, final Details details, final Participants participants, final RecruitPlan recruitPlan,
                  final Period period, final AttachedTags attachedTags, final LocalDateTime createdAt
    ) {
        if (recruitPlan.hasEnrollmentEndDate() && period.isEndBeforeThan(recruitPlan.getEnrollmentEndDate())) {
            throw new InvalidPeriodException();
        }

        if (period.isStartBeforeThan(createdAt.toLocalDate()) ||
                recruitPlan.isRecruitingBeforeThan(createdAt.toLocalDate())) {
            throw new InvalidPeriodException();
        }

        this.id = id;
        this.details = details;
        this.participants = participants;
        this.recruitPlan = recruitPlan;
        this.period = period;
        this.createdAt = createdAt;
        this.attachedTags = attachedTags;
    }

    public boolean isWritableReviews(final Long memberId) {
        return participants.isParticipated(new Participant(memberId)) && !details.isPreparingStudy();
    }

    public void participate(final Long memberId) {
        if (recruitPlan.isCloseEnrollment() || participants.isNotParticipable(memberId)) {
            throw new FailureParticipationException();
        }

        participants.participate(new Participant(memberId));

        if (participants.isFullOfMember()) {
            recruitPlan.closeRecruiting();
        }
    }

    public boolean isNeedToCloseRecruiting(LocalDate now) {
        return recruitPlan.isNeedToCloseRecruiting(now);
    }

    public void closeEnrollment() {
        recruitPlan.closeRecruiting();
    }

    public boolean isCloseEnrollment() {
        return recruitPlan.isCloseEnrollment();
    }
}
