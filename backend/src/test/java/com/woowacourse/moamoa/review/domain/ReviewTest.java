package com.woowacourse.moamoa.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.review.service.exception.UnwrittenReviewException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReviewTest {

    private final Long writtenMemberId = 1L;
    private final Long unwrittenMemberId = 2L;

    @DisplayName("리뷰 내용을 수정한다.")
    @Test
    void updateReviewContent() {
        final Review review = new Review(new AssociatedStudy(1L), new Reviewer(writtenMemberId), "content");
        final String updatedContent = "update content";

        review.updateContent(new Reviewer(writtenMemberId), updatedContent);

        assertThat(review.getContent()).isEqualTo(updatedContent);
    }

    @DisplayName("리뷰 작성자가 아니면 수정할 수 없다.")
    @Test
    void updateReviewException() {
        final Review review = new Review(new AssociatedStudy(1L), new Reviewer(writtenMemberId), "content");
        final String updatedContent = "update content";
        final Reviewer reviewer = new Reviewer(unwrittenMemberId);

        assertThatThrownBy(() -> review.updateContent(reviewer, updatedContent))
                .isInstanceOf(UnwrittenReviewException.class);
    }

    @DisplayName("리뷰 내용을 삭제하면 deleted가 true가 된다..")
    @Test
    void deleteReview() {
        final Review review = new Review(new AssociatedStudy(1L), new Reviewer(writtenMemberId), "content");

        review.delete(new Reviewer(writtenMemberId));

        assertThat(review.isDeleted()).isTrue();
    }

    @DisplayName("리뷰 작성자가 아니면 수정할 수 없다.")
    @Test
    void deleteReviewException() {
        final Review review = new Review(new AssociatedStudy(1L), new Reviewer(writtenMemberId), "content");
        final Reviewer reviewer = new Reviewer(unwrittenMemberId);

        assertThatThrownBy(() -> review.delete(reviewer))
                .isInstanceOf(UnwrittenReviewException.class);
    }
}
