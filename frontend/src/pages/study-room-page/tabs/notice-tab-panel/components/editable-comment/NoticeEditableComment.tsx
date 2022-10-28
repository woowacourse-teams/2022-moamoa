import { useState } from 'react';
import { useQueryClient } from 'react-query';

import type { ArticleId, ReviewId, StudyId } from '@custom-types';

import { useDeleteNoticeComment } from '@api/notice/comment';
import { QK_NOTICE_COMMENTS_INFINITE_SCROLL } from '@api/notice/comments';

import Comment, { type CommentProps } from '@study-room-page/components/comment/Comment';

import CommentEditForm from '@notice-tab/components/comment-edit-form/NoticeCommentEditForm';

type NoticeEditableCommentProps = { id: ReviewId; articleId: ArticleId; studyId: StudyId } & Omit<
  CommentProps,
  'onEditCommentButtonClick' | 'onDeleteCommentButtonClick'
>;
const NoticeEditableComment: React.FC<NoticeEditableCommentProps> = ({
  id,
  studyId,
  articleId,
  author,
  date,
  content,
  isMyComment,
}) => {
  const [isEditing, setIsEditing] = useState(false);
  const { mutateAsync: deleteComment } = useDeleteNoticeComment();
  const queryClient = useQueryClient();
  const refetchComments = () => {
    queryClient.refetchQueries([QK_NOTICE_COMMENTS_INFINITE_SCROLL, studyId, articleId]);
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
    deleteComment({ noticeCommentId: id, articleId, studyId })
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
          noticeCommentId={id}
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

export default NoticeEditableComment;
