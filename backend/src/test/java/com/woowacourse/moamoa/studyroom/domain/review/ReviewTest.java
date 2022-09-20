package com.woowacourse.moamoa.studyroom.domain.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.studyroom.domain.review.exception.ReviewNotWrittenInTheStudyException;
import com.woowacourse.moamoa.studyroom.domain.review.exception.UnwrittenReviewException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReviewTest {

    private final Long writtenMemberId = 1L;
    private final Long unwrittenMemberId = 2L;

    @DisplayName("리뷰 내용을 수정한다.")
    @Test
    void updateReviewContent() {
        final AssociatedStudy associatedStudy = new AssociatedStudy(1L);
        final Reviewer reviewer = new Reviewer(writtenMemberId);
        final Review review = new Review(associatedStudy, reviewer, "content");
        final String updatedContent = "update content";

        review.updateContent(associatedStudy, reviewer, updatedContent);

        assertThat(review.getContent()).isEqualTo(updatedContent);
    }

    @DisplayName("리뷰 작성자가 아니면 수정할 수 없다.")
    @Test
    void updateReviewException() {
        final AssociatedStudy associatedStudy = new AssociatedStudy(1L);
        final Review review = new Review(associatedStudy, new Reviewer(writtenMemberId), "content");
        final String updatedContent = "update content";
        final Reviewer reviewer = new Reviewer(unwrittenMemberId);

        assertThatThrownBy(() -> review.updateContent(associatedStudy, reviewer, updatedContent))
                .isInstanceOf(UnwrittenReviewException.class);
    }

    @DisplayName("리뷰 내용을 삭제하면 deleted가 true가 된다.")
    @Test
    void deleteReview() {
        final AssociatedStudy associatedStudy = new AssociatedStudy(1L);
        final Reviewer reviewer = new Reviewer(writtenMemberId);
        final Review review = new Review(associatedStudy, new Reviewer(writtenMemberId), "content");

        review.delete(associatedStudy, reviewer);

        assertThat(review.isDeleted()).isTrue();
    }

    @DisplayName("리뷰 작성자가 아니면 삭제할 수 없다.")
    @Test
    void deleteReviewException() {
        final AssociatedStudy associatedStudy = new AssociatedStudy(1L);
        final Reviewer unwrittenReviewer = new Reviewer(unwrittenMemberId);
        final Review review = new Review(associatedStudy, new Reviewer(writtenMemberId), "content");

        assertThatThrownBy(() -> review.delete(associatedStudy, unwrittenReviewer))
                .isInstanceOf(UnwrittenReviewException.class);
    }

    @DisplayName("해당 스터디에 작성된 리뷰가 아니면 삭제할 수 없다.")
    @Test
    void unwrittenReviewInStudyException() {
        final AssociatedStudy associatedStudy = new AssociatedStudy(1L);
        final Reviewer reviewer = new Reviewer(writtenMemberId);
        final Review review = new Review(associatedStudy, reviewer, "content");
        final AssociatedStudy unassociatedStudy = new AssociatedStudy(2L);

        assertThatThrownBy(() -> review.delete(unassociatedStudy, reviewer))
                .isInstanceOf(ReviewNotWrittenInTheStudyException.class);
    }
}
