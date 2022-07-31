import { useState } from 'react';
import { useParams } from 'react-router-dom';

import { css } from '@emotion/react';

import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@study-room-page/StudyRoomPage.style';
import SideMenu from '@study-room-page/components/side-menu/SideMenu';

export type Tab = { id: string; name: string; contents: React.ReactNode };

export type Tabs = Array<Tab>;

export type TabId = Tab['id'];

const tabs: Tabs = [
  { id: 'notice', name: '공지사항', contents: '공지사항입니다.' },
  { id: 'material', name: '자료실', contents: '자료실입니다.' },
  { id: 'review', name: '후기', contents: '후기입니다.' },
];

const StudyRoomPage: React.FC = () => {
  const { studyId } = useParams();
  // TODO: 내 스터디인지 아닌지 확인하는 api가 필요

  const [activeTabId, setActiveTabId] = useState<TabId>(tabs[0].id);

  const currentTab = tabs.find(({ id }) => id === activeTabId);

  const handleTabButtonClick = ({ currentTarget: { id } }: React.MouseEvent<HTMLButtonElement>) => {
    setActiveTabId(id);
  };

  return (
    <div>
      <Wrapper>
        <S.Container>
          <SideMenu
            css={css`
              position: sticky;
              top: 100px;
              left: 0;

              align-self: flex-start;
            `}
            activeTabId={activeTabId}
            tabs={tabs}
            handleTabButtonClick={handleTabButtonClick}
          />
          <S.Contents>{currentTab?.contents ?? '%ERROR% 새로고침 해주세요'}</S.Contents>
        </S.Container>
      </Wrapper>
    </div>
  );
};

export default StudyRoomPage;
