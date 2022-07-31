import * as S from '@review-page/components/review-comment/ReviewComment.style';
import useReviewComment from '@review-page/components/review-comment/useReviewComment';

import type { DateYMD, Member, ReviewId } from '@custom-types';

import Avatar from '@components/avatar/Avatar';
import DotDotDot from '@components/dotdotdot/DotDotDot';

export type ReviewCommentProps = {
  id: ReviewId;
  author: Member;
  date: DateYMD;
  content: string;
};

const ReviewComment: React.FC<ReviewCommentProps> = ({ id, author, date, content }) => {
  const { isOpen, handleDropDownClick, handleEditReviewBtnClick, handleDeleteReviewBtnClick } = useReviewComment(id);

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
            <ul className="menu">
              <li>
                <button onClick={handleEditReviewBtnClick}>수정</button>
              </li>
              <li>
                <button onClick={handleDeleteReviewBtnClick}>삭제</button>
              </li>
            </ul>
          </S.DropDown>
        </div>
      </div>
      <div className="bottom">
        <div className="content">{content}</div>
      </div>
    </S.ReviewComment>
  );
};

export default ReviewComment;
