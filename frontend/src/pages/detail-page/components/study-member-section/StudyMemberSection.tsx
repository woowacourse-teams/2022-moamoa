import { useState } from 'react';

import { DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT } from '@constants';

import { changeDateSeperator } from '@utils';

import type { StudyDetail } from '@custom-types';

import { CrownSvg } from '@components/svg';

import MoreButton from '@detail-page/components/more-button/MoreButton';
import StudyMemberCard from '@detail-page/components/study-member-card/StudyMemberCard';
import * as S from '@detail-page/components/study-member-section/StudyMemberSection.style';

export type StudyMemberSectionProps = {
  owner: StudyDetail['owner'];
  members: StudyDetail['members'];
};

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
              <CrownSvg />
              <StudyMemberCard
                username={owner.username}
                imageUrl={owner.imageUrl}
                // TODO: api 완성 되면 변경
                startDate={(owner.participationDate && changeDateSeperator(owner.participationDate)) ?? '2022.08.19'}
                studyCount={owner.numberOfStudy ?? 10}
              />
            </a>
          </S.Owner>
          {members.map(({ id, username, imageUrl, profileUrl, participationDate, numberOfStudy }) => (
            <li key={id}>
              <a href={profileUrl}>
                <StudyMemberCard
                  username={username}
                  imageUrl={imageUrl}
                  startDate={changeDateSeperator(participationDate)}
                  studyCount={numberOfStudy}
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
            <CrownSvg />
            <StudyMemberCard
              username={owner.username}
              imageUrl={owner.imageUrl}
              startDate={changeDateSeperator(owner.participationDate)}
              studyCount={10}
            />
          </a>
        </S.Owner>
        {members
          .slice(0, DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT - 1)
          .map(({ id, username, imageUrl, profileUrl, participationDate }) => (
            <li key={id}>
              <a href={profileUrl}>
                <StudyMemberCard
                  username={username}
                  imageUrl={imageUrl}
                  startDate={changeDateSeperator(participationDate)}
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
