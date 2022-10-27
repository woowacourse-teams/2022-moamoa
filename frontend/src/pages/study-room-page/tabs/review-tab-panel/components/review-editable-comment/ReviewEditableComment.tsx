import { useState } from 'react';
import { useQueryClient } from 'react-query';

import type { ReviewId, StudyId } from '@custom-types';

import { useDeleteReview } from '@api/review';
import { QK_STUDY_REVIEWS } from '@api/reviews';

import Comment, { CommentProps } from '@study-room-page/components/comment/Comment';

import ReviewEditForm from '@review-tab/components/review-edit-form/ReviewEditForm';

type ReviewEditableCommentProps = { id: ReviewId; studyId: StudyId } & Omit<
  CommentProps,
  'onEditCommentButtonClick' | 'onDeleteCommentButtonClick'
>;
const ReviewEditableComment: React.FC<ReviewEditableCommentProps> = ({
  id,
  studyId,
  author,
  date,
  content,
  isMyComment,
}) => {
  const [isEditing, setIsEditing] = useState(false);
  const { mutateAsync: deleteComment } = useDeleteReview();
  const queryClient = useQueryClient();
  const refetchReviews = () => {
    queryClient.refetchQueries([QK_STUDY_REVIEWS, studyId]);
  };

  // EditForm Handlers
  const handleCancelEditButtonClick = () => {
    setIsEditing(false);
  };
  const handleEditSuccess = () => {
    alert('성공적으로 수정했습니다!');
    setIsEditing(false);
    refetchReviews();
  };
  const handleEditError = () => {
    alert('수정에 에러가 발생했습니다!');
  };

  // Comment Handlers
  const handleEditCommentButtonClick = () => {
    setIsEditing(true);
  };
  const handleDeleteCommentButtonClick = () => {
    deleteComment({ reviewId: id, studyId })
      .then(() => {
        alert('성공적으로 삭제되었습니다');
        refetchReviews();
      })
      .catch(() => {
        alert('알수없는 에러가 발생했습니다');
      });
  };

  return (
    <>
      {isEditing ? (
        <ReviewEditForm
          reviewId={id}
          studyId={studyId}
          originalContent={content}
          date={date}
          author={author}
          onEditSuccess={handleEditSuccess}
          onEditError={handleEditError}
          onCancelEditBtnClick={handleCancelEditButtonClick}
        />
      ) : (
        <Comment
          author={author}
          date={date}
          content={content}
          isMyComment={isMyComment}
          onEditCommentButtonClick={handleEditCommentButtonClick}
          onDeleteCommentButtonClick={handleDeleteCommentButtonClick}
        />
      )}
    </>
  );
};

export default ReviewEditableComment;
