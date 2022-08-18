package com.woowacourse.moamoa.study.domain;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@ToString
public class Participant {

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(updatable = false, nullable = false)
    private LocalDate participationDate;

    public Participant(final Long memberId) {
        this.memberId = memberId;
        this.participationDate = LocalDate.now();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Participant that = (Participant) o;
        return Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}
