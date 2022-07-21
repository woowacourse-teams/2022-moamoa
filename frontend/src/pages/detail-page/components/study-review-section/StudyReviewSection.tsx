import { DEFAULT_LOAD_STUDY_REVIEW_COUNT } from '@constants';
import { useState } from 'react';

import * as S from '@pages/detail-page/components/study-review-section/StudyReviewSection.style';
import useFetchStudyReviews from '@pages/detail-page/hooks/useFetchStudyReviews';

import MoreButton from '@detail-page/components/more-button/MoreButton';
import StudyReviewCard from '@detail-page/components/study-review-card/StudyReviewCard';

export interface StudyReviewSectionProps {
  studyId: number;
}

const StudyReviewSection: React.FC<StudyReviewSectionProps> = ({ studyId }) => {
  const [isMoreButtonVisible, setIsMoreButtonVisible] = useState<boolean>(true);
  const showAll = !isMoreButtonVisible;
  const size = showAll ? undefined : DEFAULT_LOAD_STUDY_REVIEW_COUNT;
  const { data, isError, isFetching } = useFetchStudyReviews(Number(studyId), size);

  const handleMoreBtnClick = () => {
    setIsMoreButtonVisible(prev => !prev);
  };

  return (
    <S.ReviewSection>
      <S.ReviewTitle>후기 {data?.totalResults && <span>{data.totalResults}개</span>}</S.ReviewTitle>
      {isFetching && <div>로딩중...</div>}
      {isError && <div>후기 불러오기를 실패했습니다.</div>}
      {data?.reviews && (
        <>
          <S.ReviewList>
            {data.reviews.map(review => (
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
          {data.reviews.length >= DEFAULT_LOAD_STUDY_REVIEW_COUNT && (
            <S.MoreButtonContainer>
              <MoreButton
                status={showAll ? 'unfold' : 'fold'}
                onClick={handleMoreBtnClick}
                foldText="- 접기"
                unfoldText="+ 더보기"
              />
            </S.MoreButtonContainer>
          )}
        </>
      )}
    </S.ReviewSection>
  );
};

export default StudyReviewSection;
