import { useState } from 'react';

import { DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT } from '@constants';

import { changeDateSeperator } from '@utils/dates';

import { Member } from '@custom-types/index';

import StudyMemberCard from '@pages/detail-page/components/study-member-card/StudyMemberCard';
import * as S from '@pages/detail-page/components/study-member-section/StudyMemberSection.style';

import MoreButton from '@detail-page/components/more-button/MoreButton';

export interface StudyMemberSectionProps {
  members: Array<Member>;
}

const StudyMemberSection: React.FC<StudyMemberSectionProps> = ({ members }) => {
  const [showAll, setShowAll] = useState<boolean>(false);

  const handleShowMoreBtnClick = () => {
    setShowAll(prev => !prev);
  };

  const renderMembers = () => {
    if (members.length === 0) {
      return <li>스터디원이 없습니다</li>;
    }

    if (showAll) {
      return members.map(({ id, username, imageUrl, profileUrl }) => (
        <li key={id}>
          <a href={profileUrl}>
            <StudyMemberCard
              username={username}
              imageUrl={imageUrl}
              startDate={changeDateSeperator('2022-07-15')}
              studyCount={10}
            />
          </a>
        </li>
      ));
    }

    return members.slice(0, DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT).map(({ id, username, imageUrl, profileUrl }) => (
      <li key={id}>
        <a href={profileUrl}>
          <StudyMemberCard
            username={username}
            imageUrl={imageUrl}
            startDate={changeDateSeperator('2022-07-15')}
            studyCount={10}
          />
        </a>
      </li>
    ));
  };

  return (
    <S.StudyMemberSection>
      <S.Title>
        스터디원 <span>{members.length}명</span>
      </S.Title>
      <S.MemberList>{renderMembers()}</S.MemberList>
      {members.length > DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT && (
        <S.MoreButtonContainer>
          <MoreButton
            status={showAll ? 'unfold' : 'fold'}
            onClick={handleShowMoreBtnClick}
            foldText="- 접기"
            unfoldText="+ 더보기"
          />
        </S.MoreButtonContainer>
      )}
    </S.StudyMemberSection>
  );
};

export default StudyMemberSection;
