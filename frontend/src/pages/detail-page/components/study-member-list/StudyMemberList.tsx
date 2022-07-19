import { DEFAULT_VISIBLE_STUDY_MEMBER_CARD_COUNT } from '@constants';
import { useState } from 'react';

import { Member } from '@custom-types/index';

import StudyMemberCard from '@pages/detail-page/components/study-member-card/StudyMemberCard';

import MoreButton from '@detail-page/components/more-button/MoreButton';
import * as S from '@detail-page/components/study-member-list/StudyMemberList.style';

const StudyMemberList = ({ members }: { members: Array<Member> }) => {
  const [showAll, setShowAll] = useState<boolean>(false);

  const handleShowMoreBtnClick = () => {
    setShowAll(prev => !prev);
  };
  return (
    <S.StudyMemberList>
      <h1 className="title">스터디원 {members.length}명</h1>
      <div className="member-list">
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
        <div className="more-button-container">
          <MoreButton
            status={showAll ? 'unfold' : 'fold'}
            onClick={handleShowMoreBtnClick}
            foldText="- 접기"
            unfoldText="+ 더보기"
          />
        </div>
      )}
    </S.StudyMemberList>
  );
};

export default StudyMemberList;
