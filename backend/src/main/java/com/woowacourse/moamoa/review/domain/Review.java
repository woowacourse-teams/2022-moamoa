package com.woowacourse.moamoa.review.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private AssociatedStudy associatedStudy;

    @Embedded
    private Reviewer reviewer;

    @Column(nullable = false)
    private String content;

    public Review(
            final AssociatedStudy associatedStudy, final Reviewer reviewer, final String content
    ) {
        this(null, associatedStudy, reviewer, content);
    }

    public static Review writeNewReview(Long studyId, Long memberId, String content) {
        return new Review(new AssociatedStudy(studyId), new Reviewer(memberId), content);
    }

    public Long getId() {
        return id;
    }
}
