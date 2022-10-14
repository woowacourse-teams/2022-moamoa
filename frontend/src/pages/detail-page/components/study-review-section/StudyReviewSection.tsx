import { useState } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { DEFAULT_LOAD_STUDY_REVIEW_COUNT } from '@constants';

import type { StudyReview } from '@custom-types';

import { mqDown } from '@styles/responsive';
import { theme } from '@styles/theme';

import { useGetStudyReviews } from '@api/reviews';

import SectionTitle from '@shared/section-title/SectionTitle';

import MoreButton, { type MoreButtonProps } from '@detail-page/components/more-button/MoreButton';
import StudyReviewCard from '@detail-page/components/study-review-card/StudyReviewCard';

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
      {(() => {
        if (isFetching) return <Loading />;
        if (isError || !isSuccess) return <Error />;
        if (data.reviews && data.reviews.length === 0) return <NoReview />;
        return (
          <>
            <ReviewList reviews={data.reviews} />
            {data.reviews.length >= DEFAULT_LOAD_STUDY_REVIEW_COUNT && (
              <LoadMoreReviewButton status={showAll ? 'unfold' : 'fold'} onClick={handleMoreButtonClick} />
            )}
          </>
        );
      })()}
    </Self>
  );
};

export default StudyReviewSection;

const Self = styled.section`
  padding: 16px;
`;

const Loading = () => <div>로딩중...</div>;

const Error = () => <div>후기 불러오기를 실패했습니다</div>;

const NoReview = () => <div>아직 작성된 후기가 없습니다</div>;

type ReviewListProps = {
  reviews: Array<StudyReview>;
};
const ReviewList: React.FC<ReviewListProps> = ({ reviews }) => {
  const style = css`
    display: flex;
    flex-wrap: wrap;
    gap: 16px 60px;

    ${mqDown('md')} {
      flex-direction: column;
      row-gap: 30px;
    }
  `;

  const itemStyle = css`
    width: calc(50% - 30px);

    ${mqDown('md')} {
      width: 100%;
    }
  `;

  return (
    <div css={style}>
      {reviews.map(review => (
        <li key={review.id} css={itemStyle}>
          <StudyReviewCard
            imageUrl={review.member.imageUrl}
            username={review.member.username}
            reviewDate={review.createdDate}
            review={review.content}
          />
        </li>
      ))}
    </div>
  );
};

type LoadMoreReviewButtonProps = {
  status: MoreButtonProps['status'];
  onClick: MoreButtonProps['onClick'];
};
const LoadMoreReviewButton: React.FC<LoadMoreReviewButtonProps> = ({ status, onClick }) => (
  <div
    css={css`
      padding: 16px;
      text-align: right;
    `}
  >
    <MoreButton status={status} onClick={onClick} foldText="- 접기" unfoldText="+ 더보기" />
  </div>
);
