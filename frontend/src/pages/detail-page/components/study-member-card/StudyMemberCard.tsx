import styled from '@emotion/styled';

import { changeDateSeperator } from '@utils';

import type { DateYMD } from '@custom-types';

import { applyHoverTranslateTransitionStyle } from '@styles/theme';

import Flex from '@shared/flex/Flex';
import UserInfoItem from '@shared/user-info-item/UserInfoItem';

export type StudyMemberCardProps = {
  username: string;
  imageUrl: string;
  studyCount: number;
  startDate: DateYMD;
};

const StudyMemberCard: React.FC<StudyMemberCardProps> = ({ username, imageUrl, studyCount, startDate }) => {
  return (
    <Self>
      <UserInfoItem src={imageUrl} name={`${username} 프로필`} size="lg">
        <UserInfoItem.Heading>{username}</UserInfoItem.Heading>
        <Flex justifyContent="space-between" columnGap="20px">
          <UserInfoItem.Content>스터디 {studyCount}개</UserInfoItem.Content>
          <UserInfoItem.Content>{changeDateSeperator(startDate)} 가입</UserInfoItem.Content>
        </Flex>
      </UserInfoItem>
    </Self>
  );
};

export default StudyMemberCard;

const Self = styled.div`
  ${applyHoverTranslateTransitionStyle()}
`;
