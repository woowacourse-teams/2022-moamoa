package com.woowacourse.moamoa.review.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Reviewer {

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    protected Reviewer() {
    }

    public Reviewer(final Long memberId) {
        this.memberId = memberId;
    }
}
