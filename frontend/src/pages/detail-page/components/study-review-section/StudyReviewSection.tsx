import { useState } from 'react';

import { DEFAULT_LOAD_STUDY_REVIEW_COUNT } from '@constants';

import { useGetStudyReviews } from '@api/reviews';

import MoreButton from '@detail-page/components/more-button/MoreButton';
import StudyReviewCard from '@detail-page/components/study-review-card/StudyReviewCard';
import * as S from '@detail-page/components/study-review-section/StudyReviewSection.style';

export type StudyReviewSectionProps = {
  studyId: number;
};

const StudyReviewSection: React.FC<StudyReviewSectionProps> = ({ studyId }) => {
  const [isMoreButtonVisible, setIsMoreButtonVisible] = useState<boolean>(true);
  const showAll = !isMoreButtonVisible;
  const size = showAll ? undefined : DEFAULT_LOAD_STUDY_REVIEW_COUNT;
  const { data, isError, isFetching, isSuccess } = useGetStudyReviews({ studyId: Number(studyId), size });

  const handleMoreButtonClick = () => {
    setIsMoreButtonVisible(prev => !prev);
  };

  const renderReviews = () => {
    if (isFetching) return <div>로딩중...</div>;
    if (!isSuccess || isError) return <div>후기 불러오기를 실패했습니다</div>;
    if (data.reviews.length === 0) return <div>아직 작성된 후기가 없습니다</div>;
    return (
      <>
        <S.ReviewList>
          {data.reviews.map(review => (
            <S.ReviewListItem key={review.id}>
              <StudyReviewCard
                imageUrl={review.member.imageUrl}
                username={review.member.username}
                reviewDate={review.createdDate}
                review={review.content}
              />
            </S.ReviewListItem>
          ))}
        </S.ReviewList>
        {data.reviews.length >= DEFAULT_LOAD_STUDY_REVIEW_COUNT && (
          <S.MoreButtonContainer>
            <MoreButton
              status={showAll ? 'unfold' : 'fold'}
              onClick={handleMoreButtonClick}
              foldText="- 접기"
              unfoldText="+ 더보기"
            />
          </S.MoreButtonContainer>
        )}
      </>
    );
  };

  return (
    <S.ReviewSection>
      <S.ReviewTitle>후기 {<span>{data?.totalCount ?? '0'}개</span>}</S.ReviewTitle>
      {renderReviews()}
    </S.ReviewSection>
  );
};

export default StudyReviewSection;
