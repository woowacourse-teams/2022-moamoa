package com.woowacourse.moamoa.study.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import com.woowacourse.moamoa.study.service.exception.FailureParticipationException;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@NoArgsConstructor(access = PROTECTED)
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
    private AttachedTags attachedTags;

    @Embedded
    private Period period;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Study(final Details details, final Participants participants,
                 final Period period, final AttachedTags attachedTags) {
        this(null, details, participants, period, attachedTags);
    }

    public Study(final Long id, final Details details, final Participants participants,
                 final Period period, final AttachedTags attachedTags) {
        this.id = id;
        this.details = details;
        this.participants = participants;
        this.period = period;
        this.createdAt = LocalDateTime.now();
        this.attachedTags = attachedTags;

        validatePeriod(period);
    }

    private void validatePeriod(final Period period) {
        if (period.isBefore(createdAt)) {
            throw new InvalidPeriodException();
        }
    }

    public void participate(final Member member) {
        final Long memberId = member.getId();
        if (details.isCloseStatus() || period.isCloseEnrollment() || participants.isImpossibleParticipation(memberId)) {
            throw new FailureParticipationException();
        }

        participants.participate(new Participant(memberId));
    }
}
