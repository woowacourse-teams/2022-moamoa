package com.woowacourse.moamoa.study.domain;

import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.data.annotation.CreatedDate;

@Entity
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

        if (period.isBefore(createdAt)) {
            throw new InvalidPeriodException();
        }
    }

    protected Study() {
    }

    public Long getId() {
        return id;
    }

    public Details getDetails() {
        return details;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Period getPeriod() {
        return period;
    }

    public Participants getParticipants() {
        return participants;
    }

    public AttachedTags getAttachedTags() {
        return attachedTags;
    }
}
