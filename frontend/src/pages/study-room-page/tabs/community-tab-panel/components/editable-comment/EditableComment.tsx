import { useState } from 'react';
import { useQueryClient } from 'react-query';

import { ArticleId, ReviewId, StudyId } from '@custom-types';

import { QK_COMMUNITY_COMMENTS_INFINITE_SCROLL, useDeleteCommunityComment } from '@api/community/comment';

import Comment, { CommentProps } from '@study-room-page/components/comment/Comment';

import CommentEditForm from '@community-tab/components/comment-edit-form/CommentEditForm';

type EditableCommentProps = { id: ReviewId; articleId: ArticleId; studyId: StudyId } & Omit<
  CommentProps,
  'onEditCommentButtonClick' | 'onDeleteCommentButtonClick'
>;
const EditableComment: React.FC<EditableCommentProps> = ({
  id,
  studyId,
  articleId,
  author,
  date,
  content,
  isMyComment,
}) => {
  const [isEditing, setIsEditing] = useState(false);
  const { mutateAsync: deleteComment } = useDeleteCommunityComment();
  const queryClient = useQueryClient();
  const refetchComments = () => {
    queryClient.refetchQueries([QK_COMMUNITY_COMMENTS_INFINITE_SCROLL, studyId, articleId]);
  };

  // EditForm Handlers
  const handleCancelEditButtonClick = () => {
    setIsEditing(false);
  };
  const handleEditSuccess = () => {
    alert('성공적으로 수정했습니다!');
    setIsEditing(false);
    refetchComments();
  };
  const handleEditError = () => {
    alert('수정에 에러가 발생했습니다!');
  };

  // Comment Handlers
  const handleEditCommentButtonClick = () => {
    setIsEditing(true);
  };
  const handleDeleteCommentButtonClick = () => {
    deleteComment({ communityCommentId: id, articleId, studyId })
      .then(() => {
        alert('성공적으로 삭제되었습니다');
        refetchComments();
      })
      .catch(() => {
        alert('알수없는 에러가 발생했습니다');
      });
  };

  return (
    <>
      {isEditing ? (
        <CommentEditForm
          communityCommentId={id}
          studyId={studyId}
          articleId={articleId}
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

export default EditableComment;
