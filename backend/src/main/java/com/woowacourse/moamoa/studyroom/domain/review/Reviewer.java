package com.woowacourse.moamoa.studyroom.domain.review;

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

    boolean isSameMemberId(final Long memberId) {
        return this.memberId.equals(memberId);
    }
}
