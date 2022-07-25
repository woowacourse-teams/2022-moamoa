package com.woowacourse.moamoa.study.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import lombok.ToString;

@Embeddable
@ToString
public class Participants {

    @Column(name = "current_member_count")
    private int size;

    @Column(name = "max_member_count")
    private Integer max;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "study_member", joinColumns = @JoinColumn(name = "study_id"))
    private List<Participant> participants = new ArrayList<>();

    protected Participants() {
    }

    public Participants(final Integer size, final Integer max,
                        final List<Participant> participants) {
        this.size = size;
        this.max = max;
        this.participants = participants;
    }

    public int getSize() {
        return size;
    }

    public int getMax() {
        return max;
    }

    public List<Participant> getParticipants() {
        return new ArrayList<>(participants);
    }

    public static Participants createByMaxSize(final Integer maxSize) {
        return new Participants(1, maxSize, new ArrayList<>());
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
        return size == that.size && Objects.equals(max, that.max) && Objects.equals(getParticipants(),
                that.getParticipants());
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, max, participants);
    }
}
