import { memo } from 'react';

import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import { applyHoverTransitionStyle } from '@styles/theme';

import UserInfoItem from '@shared/user-info-item/UserInfoItem';

export type StudyReviewCardProps = {
  imageUrl: string;
  username: string;
  reviewDate: string;
  review: string;
};

const StudyReviewCard: React.FC<StudyReviewCardProps> = ({ imageUrl, username, reviewDate, review }) => {
  return (
    <Self>
      <UserInfoItem src={imageUrl} name={username} size="lg">
        <UserInfoItem.Heading>{username}</UserInfoItem.Heading>
        <UserInfoItem.Content>{reviewDate}</UserInfoItem.Content>
      </UserInfoItem>
      <Review>{review}</Review>
    </Self>
  );
};

const Self = styled.div`
  height: 100%;
  max-height: 150px;
  padding: 8px;

  ${applyHoverTransitionStyle()}
`;

export const Review = styled.p`
  padding: 8px 8px 0;

  ${nLineEllipsis(3)}
`;

export default memo(StudyReviewCard);
