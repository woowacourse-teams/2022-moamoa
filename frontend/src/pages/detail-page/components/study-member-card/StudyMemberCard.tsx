import Avatar from '@components/avatar/Avatar';

import * as S from '@detail-page/components/study-member-card/StudyMemberCard.style';

// TODO: studyCount, startDate api 명세에 필요
export type StudyMemberCardProp = {
  username: string;
  imageUrl: string;
  studyCount?: number;
  startDate?: string;
};

const StudyMemberCard: React.FC<StudyMemberCardProp> = ({
  username,
  imageUrl,
  studyCount = 12,
  startDate = '2022.07.02',
}) => {
  return (
    <S.StudyMemberCard>
      <Avatar profileImg={imageUrl} profileAlt={`${username} 프로필 이미지`} size="sm" />
      <S.MemberDescription>
        <S.Username>{username}</S.Username>
        <S.UserStudyInfo>
          <span>스터디 {studyCount}개</span>
          <span>{startDate} 가입</span>
        </S.UserStudyInfo>
      </S.MemberDescription>
    </S.StudyMemberCard>
  );
};

export default StudyMemberCard;
