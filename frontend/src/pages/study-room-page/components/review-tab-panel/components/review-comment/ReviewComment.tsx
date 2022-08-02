import { changeDateSeperator } from '@utils';

import type { DateYMD, Member, ReviewId, StudyId } from '@custom-types';

import Avatar from '@components/avatar/Avatar';
import KebabMenu from '@components/kebab-menu/KebabMenu';

import * as S from '@study-room-page/components/review-tab-panel/components/review-comment/ReviewComment.style';
import useReviewComment from '@study-room-page/components/review-tab-panel/components/review-comment/useReviewComment';
import ReviewEditForm from '@study-room-page/components/review-tab-panel/components/review-edit-form/ReviewEditForm';

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
        <S.ReviewCommentHead>
          <S.UserInfo>
            <S.AvatarLink href={author.profileUrl}>
              <Avatar profileImg={author.imageUrl} profileAlt="EMPTY" size="sm" />
            </S.AvatarLink>
            <S.UsernameContainer>
              <S.UsernameLink href={author.profileUrl}>{author.username}</S.UsernameLink>
              <S.Date>{changeDateSeperator(date)}</S.Date>
            </S.UsernameContainer>
          </S.UserInfo>
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
        </S.ReviewCommentHead>
        <S.ReviewCommentBody>
          <S.Content>{content}</S.Content>
        </S.ReviewCommentBody>
      </S.ReviewComment>
    );
  };

  return <>{render()}</>;
};

export default ReviewComment;
