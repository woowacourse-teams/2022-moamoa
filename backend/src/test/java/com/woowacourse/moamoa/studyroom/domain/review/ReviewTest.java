package com.woowacourse.moamoa.studyroom.domain.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableException;
import com.woowacourse.moamoa.studyroom.domain.exception.UnwritableException;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
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
        final Review review = new Review(new AssociatedStudy(studyId), new Reviewer(writtenMemberId), "content");
        final String updatedContent = "update content";
        final Accessor accessor = new Accessor(writtenMemberId, studyId);

        review.updateContent(accessor, updatedContent);

        assertThat(review.getContent()).isEqualTo(updatedContent);
    }

    @DisplayName("리뷰 작성자가 아니면 수정할 수 없다.")
    @Test
    void updateReviewException() {
        final Review review = new Review(new AssociatedStudy(studyId), new Reviewer(writtenMemberId), "content");
        final Accessor accessor = new Accessor(unwrittenMemberId, studyId);

        assertThatThrownBy(() -> review.updateContent(accessor, "update content"))
                .isInstanceOf(UneditableException.class);
    }

    @DisplayName("작성자는 맞지만 해당 스터디에 작성된 리뷰가 아니면 수정할 수 없다.")
    @Test
    void  notWrittenInTheStudyCannotBeEdited() {
        final Review review = new Review(new AssociatedStudy(studyId), new Reviewer(writtenMemberId), "content");
        final Accessor accessor = new Accessor(writtenMemberId, wrongStudyId);

        assertThatThrownBy(() -> review.updateContent(accessor, "update content"))
                .isInstanceOf(UneditableException.class);
    }

    @DisplayName("리뷰 내용을 삭제하면 deleted가 true가 된다.")
    @Test
    void deleteReview() {
        final Review review = new Review(new AssociatedStudy(studyId), new Reviewer(writtenMemberId), "content");
        final Accessor accessor = new Accessor(writtenMemberId, studyId);

        review.delete(accessor);

        assertThat(review.isDeleted()).isTrue();
    }

    @DisplayName("리뷰 작성자가 아니면 삭제할 수 없다.")
    @Test
    void deleteReviewException() {
        final Review review = new Review(new AssociatedStudy(studyId), new Reviewer(writtenMemberId), "content");
        final Accessor accessor = new Accessor(unwrittenMemberId, studyId);

        assertThatThrownBy(() -> review.delete(accessor))
                .isInstanceOf(UneditableException.class);
    }

    @DisplayName("작성자는 맞지만 해당 스터디에 작성된 리뷰가 아니면 삭제할 수 없다.")
    @Test
    void unwrittenReviewInStudyException() {
        final Review review = new Review(new AssociatedStudy(studyId), new Reviewer(writtenMemberId), "content");
        final Accessor accessor = new Accessor(writtenMemberId, wrongStudyId);

        assertThatThrownBy(() -> review.delete(accessor))
                .isInstanceOf(UneditableException.class);
    }

    @DisplayName("스터디에 참여한 방장은 리뷰를 작성할 수 있다.")
    @Test
    void writeReviewByOwner() {
        final Long studyId = 1L;
        final Long ownerId = 1L;
        final Set<Long> participants = Set.of(2L, 3L);
        final Accessor accessor = new Accessor(ownerId, studyId);
        final StudyRoom studyRoom = new StudyRoom(studyId, ownerId, participants);

        Assertions.assertDoesNotThrow(() -> Review.write(studyRoom, accessor, "content"));
    }

    @DisplayName("스터디에 참여한 사용자는 리뷰를 작성할 수 있다.")
    @Test
    void writeReviewByParticipant() {
        final Long studyId = 1L;
        final Long ownerId = 1L;
        final Long memberId = 2L;
        final Set<Long> participants = Set.of(memberId, 3L);
        final Accessor accessor = new Accessor(memberId, studyId);
        final StudyRoom studyRoom = new StudyRoom(studyId, ownerId, participants);

        Assertions.assertDoesNotThrow(() -> Review.write(studyRoom, accessor, "content"));
    }

    @DisplayName("스터디에 참여하지 않은 사용자는 리뷰를 작성할 수 없다.")
    @Test
    void writeReviewByNotParticipant() {
        final Long studyId = 1L;
        final Long ownerId = 1L;
        final Set<Long> participants = Set.of(2L, 3L);
        final Long wrongMemberId = 4L;
        final Accessor accessor = new Accessor(wrongMemberId, studyId);
        final StudyRoom studyRoom = new StudyRoom(studyId, ownerId, participants);

        assertThatThrownBy(() -> Review.write(studyRoom, accessor, "content"))
                .isInstanceOf(UnwritableException.class);
    }
}
