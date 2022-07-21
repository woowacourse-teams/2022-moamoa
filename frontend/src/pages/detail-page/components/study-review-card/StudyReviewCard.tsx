import { memo } from 'react';

import Avatar from '@components/avatar/Avatar';

import * as S from '@detail-page/components/study-review-card/StudyReviewCard.style';

export type StudyReviewCardProps = {
  profileImageUrl: string;
  username: string;
  reviewDate: string;
  review: string;
};

const StudyReviewCard: React.FC<StudyReviewCardProps> = ({ profileImageUrl, username, reviewDate, review }) => {
  return (
    <S.StudyReviewCard>
      <S.AuthorContainer>
        <Avatar size="md" profileImg={profileImageUrl} profileAlt="프로필 이미지" />
        <S.AuthorInfoContainer>
          <S.AuthorName>{username}</S.AuthorName>
          <S.ReviewDate>{reviewDate}</S.ReviewDate>
        </S.AuthorInfoContainer>
      </S.AuthorContainer>
      <S.Review>{review}</S.Review>
    </S.StudyReviewCard>
  );
};

export default memo(StudyReviewCard);
