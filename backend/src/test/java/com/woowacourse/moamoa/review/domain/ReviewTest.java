package com.woowacourse.moamoa.review.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReviewTest {

    private final Long writtenMemberId = 1L;
    private final Long unwrittenMemberId = 2L;

    @DisplayName("리뷰 작성자면 true를 반환한다.")
    @Test
    void getTrueByReviewer() {
        final Review review = new Review(new AssociatedStudy(1L), new Reviewer(writtenMemberId), "content");
        assertThat(review.isReviewer(writtenMemberId)).isTrue();
    }

    @DisplayName("리뷰 작성자가 아니면 false를 반환한다.")
    @Test
    void getFalseByNotReviewer() {
        final Review review = new Review(new AssociatedStudy(1L), new Reviewer(writtenMemberId), "content");
        assertThat(review.isReviewer(unwrittenMemberId)).isFalse();
    }
}
