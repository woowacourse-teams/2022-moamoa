import { useState } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT } from '@constants';

import { changeDateSeperator } from '@utils';
import tw from '@utils/tw';

import type { StudyDetail } from '@custom-types';

import { mqDown } from '@styles/responsive';
import { theme } from '@styles/theme';

import { CrownIcon } from '@components/icons';
import SectionTitle from '@components/section-title/SectionTitle';

import { default as ImportedMoreButton, MoreButtonProps } from '@detail-page/components/more-button/MoreButton';
import StudyMemberCard from '@detail-page/components/study-member-card/StudyMemberCard';

export type StudyMemberSectionProps = {
  owner: StudyDetail['owner'];
  members: StudyDetail['members'];
};

const StudyMemberSection: React.FC<StudyMemberSectionProps> = ({ owner, members }) => {
  const [showAll, setShowAll] = useState<boolean>(false);

  const totalMembers = [owner, ...members];

  const hasStudyMembers = !(totalMembers.length === 0);

  const isOverDefaultMemberCount = totalMembers.length > DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT;

  const handleShowMoreButtonClick = () => {
    setShowAll(prev => !prev);
  };

  return (
    <section css={tw`p-16`}>
      <SectionTitle>
        스터디원 <span css={tw`text-[${theme.fontSize.md}]`}>{totalMembers.length}명</span>
      </SectionTitle>
      <StudyMemberList>
        {!hasStudyMembers && <NoStudyMember />}
        {hasStudyMembers && (
          <StudyMemberListItems
            size={showAll ? members.length : DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT}
            owner={owner}
            members={members}
          />
        )}
      </StudyMemberList>
      {isOverDefaultMemberCount && (
        <MoreButton
          status={showAll ? 'unfold' : 'fold'}
          onClick={handleShowMoreButtonClick}
          foldText="- 접기"
          unfoldText="+ 더보기"
        />
      )}
    </section>
  );
};

const StudyMemberList = styled.ul`
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

const NoStudyMember = () => <li>스터디원이 없습니다</li>;

type StudyMemberListItemsProps = { size: number } & StudyMemberSectionProps;
const StudyMemberListItems: React.FC<StudyMemberListItemsProps> = ({ size, owner, members }) => (
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
    {members.slice(0, size).map(({ id, username, imageUrl, profileUrl, participationDate, numberOfStudy }) => (
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

const MoreButton: React.FC<MoreButtonProps> = ({ ...props }) => {
  const style = css`
    text-align: right;
    padding-top: 15px;
    padding-bottom: 15px;
  `;
  return (
    <div css={style}>
      <ImportedMoreButton {...props} />
    </div>
  );
};

export default StudyMemberSection;
