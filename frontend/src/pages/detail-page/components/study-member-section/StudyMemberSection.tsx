import { useState } from 'react';

import { DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT } from '@constants';

import { changeDateSeperator } from '@utils';

import type { Member, Owner } from '@custom-types';

import MoreButton from '@detail-page/components/more-button/MoreButton';
import StudyMemberCard from '@detail-page/components/study-member-card/StudyMemberCard';
import * as S from '@detail-page/components/study-member-section/StudyMemberSection.style';

export type StudyMemberSectionProps = {
  owner: Owner;
  members: Array<Member>;
};

const TbCrown = () => (
  <svg
    stroke="currentColor"
    fill="none"
    strokeWidth="2"
    viewBox="0 0 24 24"
    strokeLinecap="round"
    strokeLinejoin="round"
    height="20"
    width="20"
    xmlns="http://www.w3.org/2000/svg"
  >
    <desc></desc>
    <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
    <path d="M12 6l4 6l5 -4l-2 10h-14l-2 -10l5 4z"></path>
  </svg>
);

const StudyMemberSection: React.FC<StudyMemberSectionProps> = ({ owner, members }) => {
  const [showAll, setShowAll] = useState<boolean>(false);

  const totalMembers = [owner, ...members];

  const handleShowMoreButtonClick = () => {
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
              <TbCrown />
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
            <TbCrown />
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
      {totalMembers.length > DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT && (
        <S.MoreButtonContainer>
          <MoreButton
            status={showAll ? 'unfold' : 'fold'}
            onClick={handleShowMoreButtonClick}
            foldText="- 접기"
            unfoldText="+ 더보기"
          />
        </S.MoreButtonContainer>
      )}
    </S.StudyMemberSection>
  );
};

export default StudyMemberSection;
