import { useState } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT } from '@constants';

import { changeDateSeperator } from '@utils';

import { type StudyDetail } from '@custom-types';

import { mqDown } from '@styles/responsive';
import { theme } from '@styles/theme';

import { CrownIcon } from '@components/icons';
import SectionTitle from '@components/section-title/SectionTitle';

import { default as ImportedMoreButton, MoreButtonProps } from '@detail-page/components/more-button/MoreButton';
import StudyMemberCard, { StudyMemberCardProps } from '@detail-page/components/study-member-card/StudyMemberCard';

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
    <Self>
      <SectionTitle>
        <StudyMemberCount count={totalMembers.length} />
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
    </Self>
  );
};

const Self = styled.div`
  padding: 16px;
`;

type StudyMemberCountProps = {
  count: number;
};
const StudyMemberCount: React.FC<StudyMemberCountProps> = ({ count }) => (
  <>
    스터디원
    <span
      css={css`
        font-size: ${theme.fontSize.md};
      `}
    >
      {count}명
    </span>
  </>
);

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
    <li
      key={owner.id}
      css={css`
        position: relative;
      `}
    >
      <StudyOwnerCardLink
        profileUrl={owner.profileUrl}
        username={owner.username}
        imageUrl={owner.imageUrl}
        startDate={owner.participationDate}
        studyCount={owner.numberOfStudy}
      />
    </li>
    {members
      .slice(0, size)
      .map(({ id, username, imageUrl, profileUrl, participationDate: startDate, numberOfStudy }) => (
        <li key={id}>
          <StudyMemberCardLink
            profileUrl={profileUrl}
            username={username}
            imageUrl={imageUrl}
            startDate={startDate}
            studyCount={numberOfStudy}
          />
        </li>
      ))}
  </>
);

type StudyOwnerCardLinkProps = { profileUrl: string } & StudyMemberCardProps;
const StudyOwnerCardLink: React.FC<StudyOwnerCardLinkProps> = ({
  profileUrl,
  username,
  imageUrl,
  startDate,
  studyCount,
}) => (
  <a href={profileUrl}>
    <CrownIcon custom={{ position: 'absolute', top: 0, left: '20px', zIndex: 5 }} />
    <StudyMemberCard username={username} imageUrl={imageUrl} startDate={startDate} studyCount={studyCount} />
  </a>
);

type StudyMemberCardLinkProps = { profileUrl: string } & StudyMemberCardProps;
const StudyMemberCardLink: React.FC<StudyMemberCardLinkProps> = ({
  profileUrl,
  username,
  imageUrl,
  startDate,
  studyCount,
}) => (
  <a href={profileUrl}>
    <StudyMemberCard username={username} imageUrl={imageUrl} startDate={startDate} studyCount={studyCount} />
  </a>
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
