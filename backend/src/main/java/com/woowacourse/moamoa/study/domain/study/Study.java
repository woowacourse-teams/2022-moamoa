package com.woowacourse.moamoa.study.domain.study;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import com.woowacourse.moamoa.study.domain.studytag.AttachedTag;
import com.woowacourse.moamoa.study.domain.studytag.StudyTag;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.data.annotation.CreatedDate;

@Entity
public class Study {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private Details details;

    @Embedded
    private Participants participants;

    @Embedded
    private Period period;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner_id")
    private Member owner;

    @ElementCollection
    @CollectionTable(name = "study_tag", joinColumns = @JoinColumn(name = "study_id"))
    private List<AttachedTag> attachedTags = new ArrayList<>();

    @OneToMany(mappedBy = "study")
    private List<StudyTag> studyTags = new ArrayList<>();

    public Study(final Long id, final Details details) {
        this.id = id;
        this.details = details;
    }

    public Study(final Details details, final Participants participants, final Member owner,
                 final Period period, final List<AttachedTag> attachedTags) {
        this(null, details, participants, period, owner, attachedTags);
    }

    public Study(final Long id,
                 final Details details, final Participants participants,
                 final Period period, final Member owner,
                 final List<AttachedTag> attachedTags) {
        this.id = id;
        this.details = details;
        this.participants = participants;
        this.period = period;
        this.createdAt = LocalDateTime.now();
        this.owner = owner;
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

    public Member getOwner() {
        return owner;
    }

    public Participants getParticipants() {
        return participants;
    }

    public List<AttachedTag> getAttachedTags() {
        return attachedTags;
    }
}
