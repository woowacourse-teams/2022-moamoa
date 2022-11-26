import React from 'react';

import { Member, StudyId, StudyReview } from '@custom-types';

import Divider from '@shared/divider/Divider';

import ReviewEditableComment from '@review-tab/components/review-editable-comment/ReviewEditableComment';

type ReviewListProps = {
  reviews: Array<StudyReview>;
  studyId: StudyId;
  userInfo: Member;
};
const ReviewList: React.FC<ReviewListProps> = ({ reviews, studyId, userInfo }) => (
  <ul>
    {reviews.map(review => (
      <React.Fragment key={review.id}>
        <li>
          <ReviewEditableComment
            id={review.id}
            studyId={studyId}
            author={review.member}
            date={review.createdDate}
            content={review.content}
            isMyComment={userInfo.id === review.member.id}
          />
        </li>
        <Divider space="30px" />
      </React.Fragment>
    ))}
  </ul>
);

export default ReviewList;
