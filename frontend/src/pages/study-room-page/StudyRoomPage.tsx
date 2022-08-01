import { useState } from 'react';
import { useParams } from 'react-router-dom';

import { css } from '@emotion/react';

import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@study-room-page/StudyRoomPage.style';
import SideMenu from '@study-room-page/components/side-menu/SideMenu';

export type Tab = { id: string; name: string; content: React.ReactNode };

export type Tabs = Array<Tab>;

export type TabId = Tab['id'];

const tabs: Tabs = [
  { id: 'notice', name: '공지사항', content: '공지사항입니다.' },
  { id: 'material', name: '자료실', content: '자료실입니다.' },
  { id: 'review', name: '후기', content: '후기입니다.' },
];

const StudyRoomPage: React.FC = () => {
  const { studyId } = useParams() as { studyId: string };
  // TODO: 내 스터디인지 아닌지 확인하는 api가 필요

  const [activeTabId, setActiveTabId] = useState<TabId>(tabs[0].id);

  const activeTab = tabs.find(({ id }) => id === activeTabId);

  const handleTabButtonClick = (id: string) => () => {
    setActiveTabId(id);
  };

  return (
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
          onTabButtonClick={handleTabButtonClick}
        />
        <S.Content>{activeTab?.content ?? '%ERROR% 새로고침 해주세요'}</S.Content>
      </S.Container>
    </Wrapper>
  );
};

export default StudyRoomPage;
