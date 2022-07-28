package com.woowacourse.moamoa.study.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
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

    public void participate(final Member member) {
        checkStudyParticipating(member.getId());

        final Participant participant = new Participant(member.getId());
        participants.participate(participant);
    }

    private void checkStudyParticipating(Long memberId) {
        details.checkClosingRecruitment();
        period.checkParticipatingPeriod();
        participants.checkParticipating(memberId);
    }

    private void validatePeriod(final Period period) {
        if (period.isBefore(createdAt)) {
            throw new InvalidPeriodException();
        }
    }
}
