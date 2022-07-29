package com.woowacourse.moamoa.study.domain;

import static com.woowacourse.moamoa.study.domain.RecruitStatus.CLOSE;
import static com.woowacourse.moamoa.study.domain.RecruitStatus.OPEN;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
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

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private RecruitStatus recruitStatus;

    @ElementCollection
    @CollectionTable(name = "study_member", joinColumns = @JoinColumn(name = "study_id"))
    private Set<Participant> participants = new HashSet<>();

    public Participants(final Integer size, final Integer max,
                        final Set<Participant> participants, Long ownerId, RecruitStatus recruitStatus) {
        this.size = size;
        this.max = max;
        this.participants = participants;
        this.ownerId = ownerId;
        this.recruitStatus = recruitStatus;
    }

    public static Participants createByMaxSizeAndOwnerId(final Integer maxSize, Long ownerId) {
        return new Participants(1, maxSize, new HashSet<>(), ownerId, OPEN);
    }

    public List<Participant> getParticipants() {
        return new ArrayList<>(participants);
    }

    public int getCurrentMemberSize() {
        return size;
    }

    public RecruitStatus getRecruitStatus() {
        return recruitStatus;
    }

    void participate(final Participant participant) {
        participants.add(participant);
        size = size + 1;
        closeRecruitStatus();
    }

    private void closeRecruitStatus() {
        if (size == max) {
            this.recruitStatus = CLOSE;
        }
    }

    boolean isImpossibleParticipation(Long memberId) {
        return isInvalidMemberSize() || isAlreadyParticipation(memberId) || isCloseStatus();
    }

    private boolean isInvalidMemberSize() {
        return max != null && max <= size;
    }

    private boolean isAlreadyParticipation(final Long memberId) {
        final Participant participant = new Participant(memberId);
        return isOwner(memberId) || isParticipated(participant);
    }

    private boolean isCloseStatus() {
        return recruitStatus.equals(CLOSE);
    }

    private boolean isOwner(final Long memberId) {
        return Objects.equals(memberId, ownerId);
    }

    private boolean isParticipated(final Participant participant) {
        return participants.contains(participant);
    }

    public boolean contains(final Participant participant) {
        return participants.contains(participant) || ownerId.equals(participant.getMemberId());
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
