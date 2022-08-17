package com.woowacourse.moamoa.study.domain;

import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.study.service.exception.FailureParticipationException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
public class Participants {

    @Column(name = "current_member_count")
    private int size;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @ElementCollection
    @CollectionTable(name = "study_member", joinColumns = @JoinColumn(name = "study_id"))
    private Set<Participant> participants = new HashSet<>();

    public Participants(Long ownerId, final Set<Long> participants) {
        this.size = participants.size() + 1;
        this.ownerId = ownerId;
        this.participants = participants.stream()
                .map(Participant::new)
                .collect(Collectors.toSet());
    }

    public static Participants createBy(Long ownerId) {
        return new Participants(ownerId, new HashSet<>());
    }

    void participate(Long memberId) {
        if (isParticipationOrOwner(memberId)) {
            throw new FailureParticipationException();
        }

        participants.add(new Participant(memberId));
        size++;
    }

    public void leave(final Participant participant) {
        participants.remove(participant);
        size--;
    }

    boolean isParticipationOrOwner(final Long memberId) {
        return participants.contains(new Participant(memberId)) || isOwner(memberId);
    }

    boolean isParticipation(final Long memberId) {
        return participants.contains(new Participant(memberId));
    }

    boolean isOwner(Long memberId) {
        return ownerId.equals(memberId);
    }

    public int getSize() {
        return size;
    }

    private Set<Participant> getParticipants() {
        Set<Participant> totalParticipants = new HashSet<>();
        totalParticipants.add(new Participant(ownerId));
        totalParticipants.addAll(this.participants);
        return totalParticipants;
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
        return size == that.size && Objects.equals(getParticipants(), that.getParticipants());
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, getParticipants());
    }
}
