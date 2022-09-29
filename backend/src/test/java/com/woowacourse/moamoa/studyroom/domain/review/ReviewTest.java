package com.woowacourse.moamoa.studyroom.domain.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.review.exception.ReviewNotWrittenInTheStudyException;
import com.woowacourse.moamoa.studyroom.domain.review.exception.UnwrittenReviewException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReviewTest {

    private final Long writtenMemberId = 1L;
    private final Long unwrittenMemberId = 2L;
    private final Long studyId = 1L;
    private final Long wrongStudyId = 2L;

    @DisplayName("리뷰 내용을 수정한다.")
    @Test
    void updateReviewContent() {
        final AssociatedStudy associatedStudy = new AssociatedStudy(studyId);
        final Reviewer reviewer = new Reviewer(writtenMemberId);
        final Review review = new Review(associatedStudy, reviewer, "content");
        final String updatedContent = "update content";
        final Accessor accessor = new Accessor(writtenMemberId, studyId);

        review.updateContent(accessor, updatedContent);

        assertThat(review.getContent()).isEqualTo(updatedContent);
    }

    @DisplayName("리뷰 작성자가 아니면 수정할 수 없다.")
    @Test
    void updateReviewException() {
        final AssociatedStudy associatedStudy = new AssociatedStudy(studyId);
        final Review review = new Review(associatedStudy, new Reviewer(writtenMemberId), "content");
        final String updatedContent = "update content";
        final Reviewer reviewer = new Reviewer(unwrittenMemberId);
        final Accessor accessor = new Accessor(unwrittenMemberId, studyId);

        assertThatThrownBy(() -> review.updateContent(accessor, updatedContent))
                .isInstanceOf(UnwrittenReviewException.class);
    }

    @DisplayName("작성자는 맞지만 해당 스터디에 작성된 리뷰가 아니면 수정할 수 없다.")
    @Test
    void  notWrittenInTheStudyCannotBeEdited() {
        final AssociatedStudy associatedStudy = new AssociatedStudy(studyId);
        final Reviewer reviewer = new Reviewer(writtenMemberId);
        final Review review = new Review(associatedStudy, reviewer, "content");
        final AssociatedStudy unassociatedStudy = new AssociatedStudy(wrongStudyId);
        final Accessor accessor = new Accessor(writtenMemberId, wrongStudyId);

        assertThatThrownBy(() -> review.updateContent(accessor, "update content"))
                .isInstanceOf(ReviewNotWrittenInTheStudyException.class);
    }

    @DisplayName("리뷰 내용을 삭제하면 deleted가 true가 된다.")
    @Test
    void deleteReview() {
        final AssociatedStudy associatedStudy = new AssociatedStudy(studyId);
        final Reviewer reviewer = new Reviewer(writtenMemberId);
        final Review review = new Review(associatedStudy, new Reviewer(writtenMemberId), "content");
        final Accessor accessor = new Accessor(writtenMemberId, studyId);

        review.delete(accessor);

        assertThat(review.isDeleted()).isTrue();
    }

    @DisplayName("리뷰 작성자가 아니면 삭제할 수 없다.")
    @Test
    void deleteReviewException() {
        final AssociatedStudy associatedStudy = new AssociatedStudy(studyId);
        final Reviewer unwrittenReviewer = new Reviewer(unwrittenMemberId);
        final Review review = new Review(associatedStudy, new Reviewer(writtenMemberId), "content");
        final Accessor accessor = new Accessor(unwrittenMemberId, studyId);

        assertThatThrownBy(() -> review.delete(accessor))
                .isInstanceOf(UnwrittenReviewException.class);
    }

    @DisplayName("작성자는 맞지만 해당 스터디에 작성된 리뷰가 아니면 삭제할 수 없다.")
    @Test
    void unwrittenReviewInStudyException() {
        final AssociatedStudy associatedStudy = new AssociatedStudy(studyId);
        final Reviewer reviewer = new Reviewer(writtenMemberId);
        final Review review = new Review(associatedStudy, reviewer, "content");
        final AssociatedStudy unassociatedStudy = new AssociatedStudy(wrongStudyId);
        final Accessor accessor = new Accessor(writtenMemberId, wrongStudyId);

        assertThatThrownBy(() -> review.delete(accessor))
                .isInstanceOf(ReviewNotWrittenInTheStudyException.class);
    }
}
