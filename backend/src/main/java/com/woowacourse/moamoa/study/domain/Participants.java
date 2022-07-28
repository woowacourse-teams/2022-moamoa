package com.woowacourse.moamoa.study.domain;

import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.study.service.exception.InvalidParticipationStudyException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@ToString
@NoArgsConstructor(access = PROTECTED)
public class Participants {

    @Column(name = "current_member_count")
    private int size;

    @Column(name = "max_member_count")
    private Integer max;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @ElementCollection
    @CollectionTable(name = "study_member", joinColumns = @JoinColumn(name = "study_id"))
    private Set<Participant> participants = new HashSet<>();

    public Participants(final Integer size, final Integer max,
                        final Set<Participant> participants, Long ownerId) {
        this.size = size;
        this.max = max;
        this.participants = participants;
        this.ownerId = ownerId;
    }

    public List<Participant> getParticipants() {
        return new ArrayList<>(participants);
    }

    public static Participants createByMaxSizeAndOwnerId(final Integer maxSize, Long ownerId) {
        return new Participants(1, maxSize, new HashSet<>(List.of(new Participant(ownerId))), ownerId);
    }

    protected void participate(final Participant participant) {
        participants.add(participant);
        this.size = this.size + 1;
    }

    public int getCurrentMemberSize() {
        return size;
    }

    protected void checkParticipating(Long memberId) {
        if (isInvalidMemberSize() || isAlreadyParticipation(memberId)) {
            throw new InvalidParticipationStudyException();
        }
    }

    private boolean isInvalidMemberSize() {
        return max != null && max <= size;
    }

    private boolean isAlreadyParticipation(final Long memberId) {
        final Participant participant = new Participant(memberId);
        return Objects.equals(memberId, ownerId) || participants.contains(participant);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Participants that = (Participants) o;
        return size == that.size && Objects.equals(max, that.max) && Objects.equals(ownerId, that.ownerId) &&
                Objects.equals(getParticipants(), that.getParticipants());
    }

    @Override
    public int hashCode()  {
        return Objects.hash(size, max, ownerId, participants);
    }
}
