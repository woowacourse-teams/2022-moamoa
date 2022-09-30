import { useState } from 'react';

import styled from '@emotion/styled';

import { DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT } from '@constants';

import { changeDateSeperator } from '@utils';
import tw from '@utils/tw';

import type { StudyDetail } from '@custom-types';

import { mqDown } from '@styles/responsive';
import { theme } from '@styles/theme';

import { CrownIcon } from '@components/icons';
import SectionTitle from '@components/section-title/SectionTitle';

import MoreButton from '@detail-page/components/more-button/MoreButton';
import StudyMemberCard from '@detail-page/components/study-member-card/StudyMemberCard';

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
          <li key={owner.id} css={tw`relative`}>
            <a href={owner.profileUrl}>
              <div css={tw`absolute top-0 left-20 z-5`}>
                <CrownIcon />
              </div>
              <StudyMemberCard
                username={owner.username}
                imageUrl={owner.imageUrl}
                startDate={changeDateSeperator(owner.participationDate)}
                studyCount={owner.numberOfStudy}
              />
            </a>
          </li>
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
        <li key={owner.id} css={tw`relative`}>
          <a href={owner.profileUrl}>
            <div css={tw`absolute top-0 left-20 z-5`}>
              <CrownIcon />
            </div>
            <StudyMemberCard
              username={owner.username}
              imageUrl={owner.imageUrl}
              startDate={changeDateSeperator(owner.participationDate)}
              studyCount={owner.numberOfStudy}
            />
          </a>
        </li>
        {members
          .slice(0, DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT - 1)
          .map(({ id, username, imageUrl, profileUrl, participationDate, numberOfStudy }) => (
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
  };

  return (
    <section css={tw`p-16`}>
      <SectionTitle>
        스터디원 <span css={tw`text-[${theme.fontSize.md}]`}>{totalMembers.length}명</span>
      </SectionTitle>
      <MemberList>{renderMembers()}</MemberList>
      {totalMembers.length > DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT && (
        <div css={tw`text-right pt-15 pb-15`}>
          <MoreButton
            status={showAll ? 'unfold' : 'fold'}
            onClick={handleShowMoreButtonClick}
            foldText="- 접기"
            unfoldText="+ 더보기"
          />
        </div>
      )}
    </section>
  );
};

const MemberList = styled.ul`
  display: grid;
  grid-template-columns: repeat(2, minmax(auto, 1fr));
  grid-column-gap: 30px;
  grid-row-gap: 20px;

  ${mqDown('md')} {
    display: flex;
    flex-direction: column;
    row-gap: 20px;
  }
`;

export default StudyMemberSection;
