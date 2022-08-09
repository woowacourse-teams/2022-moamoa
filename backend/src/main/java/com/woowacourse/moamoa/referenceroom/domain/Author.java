package com.woowacourse.moamoa.referenceroom.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Author {

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    public Author(final Long memberId) {
        this.memberId = memberId;
    }
}
