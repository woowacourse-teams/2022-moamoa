import { memo } from 'react';

import { css } from '@emotion/react';

import Avatar from '@components/avatar/Avatar';

import * as S from '@detail-page/components/study-review-card/StudyReviewCard.style';

type StudyReviewCardProps = {
  className?: string;
  profileImageUrl: string;
  username: string;
  reviewDate: string;
  review: string;
};

const StudyReviewCard: React.FC<StudyReviewCardProps> = ({
  className,
  profileImageUrl,
  username,
  reviewDate,
  review,
}) => {
  return (
    <S.StudyReviewCard className={className}>
      <div className="author">
        <Avatar className="left" size="md" profileImg={profileImageUrl} profileAlt="프로필 이미지" />
        <div className="right">
          <div className="username">{username}</div>
          <div className="review-date">{reviewDate}</div>
        </div>
      </div>
      <div className="review">{review}</div>
    </S.StudyReviewCard>
  );
};

export default memo(StudyReviewCard);
