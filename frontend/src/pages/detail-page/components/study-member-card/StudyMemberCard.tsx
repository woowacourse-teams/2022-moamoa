import Flex from '@components/flex/Flex';
import Item from '@components/item/Item';

import * as S from '@detail-page/components/study-member-card/StudyMemberCard.style';

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
    <S.StudyMemberCard>
      <Item src={imageUrl} name={`${username} 프로필`} size="lg">
        <Item.Heading>{username}</Item.Heading>
        <Flex justifyContent="space-between" gap="20px">
          <Item.Content>스터디 {studyCount}개</Item.Content>
          <Item.Content>{startDate} 가입</Item.Content>
        </Flex>
      </Item>
    </S.StudyMemberCard>
  );
};

export default StudyMemberCard;
