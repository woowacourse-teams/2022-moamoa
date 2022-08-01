import { useState } from 'react';
import { TbCrown } from 'react-icons/tb';

import { DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT } from '@constants';

import { changeDateSeperator } from '@utils/dates';

import { Member, Owner } from '@custom-types/index';

import MoreButton from '@detail-page/components/more-button/MoreButton';
import StudyMemberCard from '@detail-page/components/study-member-card/StudyMemberCard';
import * as S from '@detail-page/components/study-member-section/StudyMemberSection.style';

export interface StudyMemberSectionProps {
  owner: Owner;
  members: Array<Member>;
}

const StudyMemberSection: React.FC<StudyMemberSectionProps> = ({ owner, members }) => {
  const [showAll, setShowAll] = useState<boolean>(false);

  const totalMembers = [owner, ...members];

  const handleShowMoreBtnClick = () => {
    setShowAll(prev => !prev);
  };

  const renderMembers = () => {
    if (totalMembers.length === 0) {
      return <li>스터디원이 없습니다</li>;
    }

    if (showAll) {
      return (
        <>
          <S.Owner key={owner.id}>
            <a href={owner.profileUrl}>
              <TbCrown size={20} />
              <StudyMemberCard
                username={owner.username}
                imageUrl={owner.imageUrl}
                startDate={changeDateSeperator('2022-07-15')}
                studyCount={10}
              />
            </a>
          </S.Owner>
          {members.map(({ id, username, imageUrl, profileUrl }) => (
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
          ))}
        </>
      );
    }

    return (
      <>
        <S.Owner key={owner.id}>
          <a href={owner.profileUrl}>
            <TbCrown size={20} />
            <StudyMemberCard
              username={owner.username}
              imageUrl={owner.imageUrl}
              startDate={changeDateSeperator('2022-07-15')}
              studyCount={10}
            />
          </a>
        </S.Owner>
        {members.slice(0, DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT - 1).map(({ id, username, imageUrl, profileUrl }) => (
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
        ))}
      </>
    );
  };

  return (
    <S.StudyMemberSection>
      <S.Title>
        스터디원 <span>{totalMembers.length}명</span>
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
