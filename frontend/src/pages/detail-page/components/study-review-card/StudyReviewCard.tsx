import { memo } from 'react';

import Item from '@design/components/item/Item';

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
      <Item src={imageUrl} name={username} size="lg">
        <Item.Heading>{username}</Item.Heading>
        <Item.Content>{reviewDate}</Item.Content>
      </Item>
      <S.Review>{review}</S.Review>
    </S.StudyReviewCard>
  );
};

export default memo(StudyReviewCard);
