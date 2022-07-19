import { css, useTheme } from '@emotion/react';

import Avatar from '@components/avatar/Avatar';

import * as S from '@detail-page/components/study-member-card/StudyMemberCard.style';

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
  return (
    <S.StudyMemberCard className={className}>
      <Avatar className="left" profileImg={profileImage} profileAlt="프로필 이미지" size="sm" />
      <div className="right">
        <h4 className="username">{username}</h4>
        <div>
          <span className="study-count">studies: {studyCount}</span>
          <span>start at: {startDate}</span>
        </div>
      </div>
    </S.StudyMemberCard>
  );
};

export default StudyMemberCard;
