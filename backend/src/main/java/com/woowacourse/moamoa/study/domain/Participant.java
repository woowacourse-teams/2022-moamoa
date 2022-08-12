package com.woowacourse.moamoa.study.domain;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Participant {

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(updatable = false, nullable = false)
    private LocalDate participationDate;

    public Participant(final Long memberId) {
        this.memberId = memberId;
        this.participationDate = LocalDate.now();
    }
}
