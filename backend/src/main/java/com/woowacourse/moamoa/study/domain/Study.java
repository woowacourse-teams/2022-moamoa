package com.woowacourse.moamoa.study.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.study.service.exception.FailureParticipationException;
import java.time.LocalDate;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
public class Study extends BaseEntity {

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
        this.attachedTags = attachedTags;
    }

    public boolean isParticipated(final Participant participant) {
        return participants.contains(participant);
    }

    public boolean isBeforeThanStudyStartDate(final LocalDate reviewCreatedDate) {
        return period.isBeforeThanStartDate(reviewCreatedDate);

    }
    public void participate(final Long memberId) {
        if (period.isCloseEnrollment() || participants.isImpossibleParticipation(memberId)) {
            throw new FailureParticipationException();
        }

        participants.participate(new Participant(memberId));
    }
}
