import { memo } from 'react';

import { css } from '@emotion/react';

import Avatar from '@components/avatar/Avatar';

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
    <div className={className}>
      <div
        css={css`
          display: flex;
          align-items: center;
          margin-bottom: 10px;
        `}
      >
        <Avatar size="md" profileImg={profileImageUrl} profileAlt="프로필 이미지" />
        <div
          css={css`
            padding-left: 12px;
          `}
        >
          <div
            css={css`
              margin-bottom: 4px;
            `}
          >
            {username}
          </div>
          <div
            css={css`
              color: #717171;
              font-size: 14px;
            `}
          >
            {reviewDate}
          </div>
        </div>
      </div>
      <div
        css={css`
          display: -webkit-box;
          line-height: 24px;
          -webkit-line-clamp: 3;
          overflow: hidden;
          text-overflow: ellipsis;
          -webkit-box-orient: vertical;
          margin-bottom: 40px;
        `}
      >
        {review}
      </div>
    </div>
  );
};

export default memo(StudyReviewCard);
