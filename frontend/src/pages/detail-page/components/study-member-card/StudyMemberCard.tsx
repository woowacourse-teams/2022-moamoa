import styled from '@emotion/styled';

import { applyHoverTransitionStyle } from '@styles/theme';

import Flex from '@components/flex/Flex';
import UserInfoItem from '@components/user-info-item/UserInfoItem';

export type StudyMemberCardProps = {
  username: string;
  imageUrl: string;
  startDate: string;
  studyCount: number;
};

const StudyMemberCard: React.FC<StudyMemberCardProps> = ({
  username,
  imageUrl,
  studyCount = 12,
  startDate = '2022.07.02',
}) => {
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
