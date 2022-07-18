import { DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT } from '@constants';
import { useState } from 'react';

import { css } from '@emotion/react';

import { mqDown } from '@utils/media-query';

import { Member } from '@custom-types/index';

import StudyMemberCard from '@pages/detail-page/components/study-member-card/StudyMemberCard';

import MoreButton from '@detail-page/components/more-button/MoreButton';

const StudyMemberList = ({ members }: { members: Array<Member> }) => {
  const [showAll, setShowAll] = useState<boolean>(false);

  const handleShowMoreBtnClick = () => {
    setShowAll(prev => !prev);
  };
  return (
    <div>
      <h1
        css={css`
          margin-bottom: 30px;
        `}
      >
        스터디원 {members.length}명
      </h1>
      <div
        css={css`
          display: grid;
          grid-template-columns: repeat(2, minmax(auto, 1fr));
          grid-column-gap: 30px;
          grid-row-gap: 20px;

          ${mqDown('md')} {
            display: flex;
            flex-direction: column;
            row-gap: 20px;
          }
        `}
      >
        {showAll
          ? members.map(({ id, username, profileImage, profileUrl }) => (
              <a key={id} href={profileUrl}>
                <StudyMemberCard username={username} profileImage={profileImage} />
              </a>
            ))
          : members
              .slice(0, DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT)
              .map(({ id, username, profileImage, profileUrl }) => (
                <a key={id} href={profileUrl}>
                  <StudyMemberCard username={username} profileImage={profileImage} />
                </a>
              ))}
      </div>
      {members.length > DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT && (
        <div
          css={css`
            text-align: center;
            padding-top: 30px;
            padding-bottom: 15px;
          `}
        >
          <MoreButton
            status={showAll ? 'unfold' : 'fold'}
            onClick={handleShowMoreBtnClick}
            foldText="- 접기"
            unfoldText="+ 더보기"
          />
        </div>
      )}
    </div>
  );
};

export default StudyMemberList;
