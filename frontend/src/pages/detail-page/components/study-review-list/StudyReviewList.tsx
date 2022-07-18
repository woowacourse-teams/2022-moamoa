import { css } from '@emotion/react';

import { mqDown } from '@utils/media-query';

import { StudyReview } from '@custom-types/index';

import StudyReviewCard from '@detail-page/components/study-review-card/StudyReviewCard';

const StudyReviewList: React.FC<{ reviews: Array<StudyReview> }> = ({ reviews }) => {
  return (
    <div
      css={css`
        display: flex;
        flex-wrap: wrap;
        column-gap: 60px;

        .study-review-card {
          width: calc(50% - 30px);
        }

        ${mqDown('md')} {
          flex-direction: column;
          row-gap: 30px;
          .study-review-card {
            width: 100%;
          }
        }
      `}
    >
      {reviews.map(review => {
        return (
          <StudyReviewCard
            className="study-review-card"
            key={review.id}
            profileImageUrl={review.member.profileImage}
            username={review.member.username}
            reviewDate={review.createdAt}
            review={review.content}
          />
        );
      })}
    </div>
  );
};

export default StudyReviewList;
