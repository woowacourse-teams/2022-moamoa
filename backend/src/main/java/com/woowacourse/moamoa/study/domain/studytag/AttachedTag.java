package com.woowacourse.moamoa.study.domain.studytag;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
public class AttachedTag {

    @Column(name = "tag_id", nullable = false)
    private Long tagId;
}
