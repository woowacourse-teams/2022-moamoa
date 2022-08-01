import { changeDateSeperator } from '@utils/dates';

import type { DateYMD, Member, ReviewId, StudyId } from '@custom-types';

import ReviewEditForm from '@pages/review-page/components/review-edit-form/ReviewEditForm';

import Avatar from '@components/avatar/Avatar';
import KebabMenu from '@components/kebab-menu/KebabMenu';

import * as S from '@review-page/components/review-comment/ReviewComment.style';
import useReviewComment from '@review-page/components/review-comment/useReviewComment';

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
    handleEditSuccess,
    handleEditError,
  } = useReviewComment(id, studyId);

  const render = () => {
    if (isEditing) {
      return (
        <ReviewEditForm
          reviewId={id}
          studyId={studyId}
          originalContent={content}
          date={date}
          author={author}
          onCancelEditBtnClick={handleCancelEditBtnClick}
          onEditSuccess={handleEditSuccess}
          onEditError={handleEditError}
        />
      );
    }

    return (
      <S.ReviewComment>
        <div className="top">
          <div className="left">
            <div className="user-info">
              <div className="left">
                <a href={author.profileUrl}>
                  <Avatar profileImg={author.imageUrl} profileAlt="EMPTY" size="sm" />
                </a>
              </div>
              <div className="right">
                <a href={author.profileUrl}>
                  <span className="username">{author.username}</span>
                </a>
                <span className="date">{changeDateSeperator(date)}</span>
              </div>
            </div>
          </div>
          <div className="right">
            <S.DropDown isOpen={isOpen}>
              <KebabMenu onClick={handleDropDownClick} />
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
          <S.Content>{content}</S.Content>
        </div>
      </S.ReviewComment>
    );
  };

  return <>{render()}</>;
};

export default ReviewComment;
