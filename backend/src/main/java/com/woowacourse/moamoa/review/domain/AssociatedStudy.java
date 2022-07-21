package com.woowacourse.moamoa.review.domain;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = PROTECTED)
public class AssociatedStudy {

    @Column(name = "study_id", nullable = false)
    private Long studyId;
}
