import { useState } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { DEFAULT_LOAD_STUDY_REVIEW_COUNT } from '@constants';

import { type StudyReview } from '@custom-types';

import { mqDown } from '@styles/responsive';
import { theme } from '@styles/theme';

import { useGetStudyReviews } from '@api/reviews';

import SectionTitle from '@components/section-title/SectionTitle';

import ImportedMoreButton, {
  type MoreButtonProps as ImportedMoreButtonProps,
} from '@detail-page/components/more-button/MoreButton';
import StudyReviewCard from '@detail-page/components/study-review-card/StudyReviewCard';

export type StudyReviewSectionProps = {
  studyId: number;
};

const StudyReviewSection: React.FC<StudyReviewSectionProps> = ({ studyId }) => {
  const [isMoreButtonVisible, setIsMoreButtonVisible] = useState<boolean>(true);
  const showAll = !isMoreButtonVisible;
  const size = showAll ? undefined : DEFAULT_LOAD_STUDY_REVIEW_COUNT;
  const { data, isError, isFetching, isSuccess } = useGetStudyReviews({ studyId: Number(studyId), size });

  const reviews = data?.reviews;
  const noReview = !isFetching && isSuccess && reviews && reviews.length === 0;
  const hasReview = (val: unknown): val is Array<StudyReview> =>
    !isFetching && isSuccess && !!reviews && reviews.length > 0;

  const handleMoreButtonClick = () => {
    setIsMoreButtonVisible(prev => !prev);
  };

  return (
    <Self>
      <SectionTitle>
        후기
        {
          <span
            css={css`
              font-size: ${theme.fontSize.md};
            `}
          >
            {data?.totalCount ?? '0'}개
          </span>
        }
      </SectionTitle>
      {isFetching && <Loading />}
      {isError && !isSuccess && <Error />}
      {noReview && <NoReview />}
      {hasReview(reviews) && (
        <>
          <ReviewList>
            {reviews.map(review => (
              <ReviewListItem key={review.id}>
                <StudyReviewCard
                  imageUrl={review.member.imageUrl}
                  username={review.member.username}
                  reviewDate={review.createdDate}
                  review={review.content}
                />
              </ReviewListItem>
            ))}
          </ReviewList>
          {reviews.length >= DEFAULT_LOAD_STUDY_REVIEW_COUNT && (
            <MoreButton
              status={showAll ? 'unfold' : 'fold'}
              onClick={handleMoreButtonClick}
              foldText="- 접기"
              unfoldText="+ 더보기"
            />
          )}
        </>
      )}
    </Self>
  );
};

const Self = styled.section`
  padding: 16px;
`;

const Loading = () => <div>로딩중...</div>;

const Error = () => <div>후기 불러오기를 실패했습니다</div>;

const NoReview = () => <div>아직 작성된 후기가 없습니다</div>;

const ReviewList = styled.ul`
  display: flex;
  flex-wrap: wrap;
  gap: 16px 60px;

  ${mqDown('md')} {
    flex-direction: column;
    row-gap: 30px;
  }
`;

const ReviewListItem = styled.li`
  width: calc(50% - 30px);

  ${mqDown('md')} {
    width: 100%;
  }
`;

type MoreButtonProps = ImportedMoreButtonProps;
const MoreButton: React.FC<MoreButtonProps> = ({ ...props }) => (
  <div
    css={css`
      padding: 16px;
      text-align: right;
    `}
  >
    <ImportedMoreButton {...props} />
  </div>
);

export default StudyReviewSection;
