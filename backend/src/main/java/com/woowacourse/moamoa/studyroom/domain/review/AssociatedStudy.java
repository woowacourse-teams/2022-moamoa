package com.woowacourse.moamoa.studyroom.domain.review;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AssociatedStudy {

    @Column(name = "study_id", nullable = false)
    private Long studyId;
}
