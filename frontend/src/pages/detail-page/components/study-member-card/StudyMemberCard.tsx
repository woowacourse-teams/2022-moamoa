import styled from '@emotion/styled';

import { type DateYMD } from '@custom-types';

import { applyHoverTransitionStyle } from '@styles/theme';

import Flex from '@components/flex/Flex';
import UserInfoItem from '@components/user-info-item/UserInfoItem';

export type StudyMemberCardProps = {
  username: string;
  imageUrl: string;
  studyCount: number;
  startDate: string;
};

const StudyMemberCard: React.FC<StudyMemberCardProps> = ({ username, imageUrl, studyCount, startDate }) => {
  return (
    <Self>
      <UserInfoItem src={imageUrl} name={`${username} 프로필`} size="lg">
        <UserInfoItem.Heading>{username}</UserInfoItem.Heading>
        <Flex justifyContent="space-between" columnGap="20px">
          <UserInfoItem.Content>스터디 {studyCount}개</UserInfoItem.Content>
          <UserInfoItem.Content>{startDate} 가입</UserInfoItem.Content>
        </Flex>
      </UserInfoItem>
    </Self>
  );
};

export const Self = styled.div`
  ${applyHoverTransitionStyle()}
`;

export default StudyMemberCard;
