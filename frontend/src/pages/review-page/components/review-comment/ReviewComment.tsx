import * as S from '@review-page/components/review-comment/ReviewComment.style';
import useReviewComment from '@review-page/components/review-comment/useReviewComment';

import type { DateYMD, Member, ReviewId, StudyId } from '@custom-types';

import ReviewEditForm from '@pages/review-page/components/review-edit-form/ReviewEditForm';

import Avatar from '@components/avatar/Avatar';
import DotDotDot from '@components/dotdotdot/DotDotDot';

export type ReviewCommentProps = {
  id: ReviewId;
  studyId: StudyId;
  author: Member;
  date: DateYMD;
  content: string;
};

const ReviewComment: React.FC<ReviewCommentProps> = ({ id, studyId, author, date, content }) => {
  const {
    isOpen,
    isEditing,
    handleDropDownClick,
    handleEditReviewBtnClick,
    handleDeleteReviewBtnClick,
    handleCancelEditBtnClick,
  } = useReviewComment(id, studyId);

  const handlePostSuccess = () => {
    alert('성공적으로 수정했습니다!');
  };
  const handlePostError = () => {
    alert('수정에 에러가 발생했습니다!');
  };

  return (
    <S.ReviewComment>
      <div className="top">
        <div className="left">
          <S.Author>
            <Avatar profileImg={author.profileUrl} profileAlt="프로필 이미지" size="sm" />
            <S.Name>{author.username}</S.Name>
          </S.Author>
        </div>
        <div className="right">
          <S.Date>{date}</S.Date>
          <S.DropDown isOpen={isOpen}>
            <DotDotDot onClick={handleDropDownClick} />
            <S.DropDownMenu>
              <li>
                <button onClick={handleEditReviewBtnClick}>수정</button>
              </li>
              <li>
                <button onClick={handleDeleteReviewBtnClick}>삭제</button>
              </li>
            </S.DropDownMenu>
          </S.DropDown>
        </div>
      </div>
      <div className="bottom">
        {isEditing ? (
          <ReviewEditForm
            reviewId={id}
            studyId={studyId}
            originalContent={content}
            onCancelEditBtnClick={handleCancelEditBtnClick}
            onPostSuccess={handlePostSuccess}
            onPostError={handlePostError}
          />
        ) : (
          <S.Content>{content}</S.Content>
        )}
      </div>
    </S.ReviewComment>
  );
};

export default ReviewComment;
