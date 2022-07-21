import { DEFAULT_LOAD_STUDY_REVIEW_COUNT } from '@constants';

import { StudyReview } from '@custom-types/index';

import MoreButton from '@detail-page/components/more-button/MoreButton';
import StudyReviewCard from '@detail-page/components/study-review-card/StudyReviewCard';
import * as S from '@detail-page/components/study-review-list/StudyReviewList.style';

export interface StudyReviewListProps {
  reviews: Array<StudyReview>;
  reviewCount: number;
  showAll: boolean;
  handleMoreBtnClick: React.MouseEventHandler<HTMLButtonElement>;
}

const StudyReviewList: React.FC<StudyReviewListProps> = ({ reviews, reviewCount, showAll, handleMoreBtnClick }) => {
  return (
    <S.ExtraInfo>
      <S.ReviewTitle>
        후기 <span>{reviewCount}개</span>
      </S.ReviewTitle>
      <S.ReviewList>
        {reviews.map(review => (
          <S.ReviewListItem key={review.id}>
            <StudyReviewCard
              profileImageUrl={review.member.profileImage}
              username={review.member.username}
              reviewDate={review.createdAt}
              review={review.content}
            />
          </S.ReviewListItem>
        ))}
      </S.ReviewList>
      {reviews.length >= DEFAULT_LOAD_STUDY_REVIEW_COUNT && (
        <S.MoreButtonContainer>
          <MoreButton
            status={showAll ? 'fold' : 'unfold'}
            onClick={handleMoreBtnClick}
            foldText="- 접기"
            unfoldText="+ 더보기"
          />
        </S.MoreButtonContainer>
      )}
    </S.ExtraInfo>
  );
};

export default StudyReviewList;
