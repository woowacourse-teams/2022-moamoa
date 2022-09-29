import { memo } from 'react';

import UserInfoItem from '@components/user-info-item/UserInfoItem';

import * as S from '@detail-page/components/study-review-card/StudyReviewCard.style';

export type StudyReviewCardProps = {
  imageUrl: string;
  username: string;
  reviewDate: string;
  review: string;
};

const StudyReviewCard: React.FC<StudyReviewCardProps> = ({ imageUrl, username, reviewDate, review }) => {
  return (
    <S.StudyReviewCard>
      <UserInfoItem src={imageUrl} name={username} size="lg">
        <UserInfoItem.Heading>{username}</UserInfoItem.Heading>
        <UserInfoItem.Content>{reviewDate}</UserInfoItem.Content>
      </UserInfoItem>
      <S.Review>{review}</S.Review>
    </S.StudyReviewCard>
  );
};

export default memo(StudyReviewCard);
