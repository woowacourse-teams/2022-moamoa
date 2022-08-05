package com.woowacourse.moamoa.review.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Reviewer {

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    public Reviewer(final Long memberId) {
        this.memberId = memberId;
    }
}
