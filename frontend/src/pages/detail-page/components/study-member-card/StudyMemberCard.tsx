import { css, useTheme } from '@emotion/react';

import Avatar from '@components/avatar/Avatar';

export type StudyMemberCardProp = {
  username: string;
  profileImage: string;
  studyCount?: number;
  startDate?: string;
  className?: string;
};

const StudyMemberCard: React.FC<StudyMemberCardProp> = ({
  className,
  username,
  profileImage,
  studyCount = 12,
  startDate = '2022-07-02',
}) => {
  const theme = useTheme();
  return (
    <div
      css={css`
        display: flex;
        padding: 12px;
        background: ${theme.colors.secondary.light};
        box-shadow: 0px 0px 2px 1px ${theme.colors.secondary.base};
        border-radius: 15px;
      `}
      className={className}
    >
      <Avatar profileImg={profileImage} profileAlt="프로필 이미지" size="sm" />
      <div
        css={css`
          padding-left: 12px;
          font-size: 20px;
        `}
      >
        <h4>{username}</h4>
        <div
          css={css`
            display: flex;
            font-size: 12px;
          `}
        >
          <span
            css={css`
              margin-right: 12px;
            `}
          >
            studies: {studyCount}
          </span>
          <span>start at: {startDate}</span>
        </div>
      </div>
    </div>
  );
};

export default StudyMemberCard;
