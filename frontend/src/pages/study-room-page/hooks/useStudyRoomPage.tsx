import type { AxiosError } from 'axios';
import { useState } from 'react';
import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import type { GetUserRoleResponseData } from '@custom-types';

import { getUserRole } from '@api';

import LinkRoomTabPanel from '@study-room-page/components/link-room-tab-panel/LinkRoomTabPanel';
import LinkItem from '@study-room-page/components/link-room-tab-panel/components/link-item/LinkItem';
import ReviewTabPanel from '@study-room-page/components/review-tab-panel/ReviewTabPanel';

export type TabId = 'notice' | 'link-room' | 'review';

export type Tab = { id: TabId; name: string; content: React.ReactNode };

export type Tabs = Array<Tab>;

const useStudyRoomPage = () => {
  const { studyId } = useParams() as { studyId: string };

  const userRoleQueryResult = useQuery<GetUserRoleResponseData, AxiosError>('my-role', () =>
    getUserRole({ studyId: Number(studyId) }),
  );

  const tabs: Tabs = [
    { id: 'notice', name: '공지사항', content: '공지사항입니다.' },
    { id: 'link-room', name: '링크 모음', content: <LinkRoomTabPanel /> },
    { id: 'review', name: '후기', content: <ReviewTabPanel studyId={Number(studyId)} /> },
  ];

  const [activeTabId, setActiveTabId] = useState<TabId>(tabs[0].id);

  const activeTab = tabs.find(({ id }) => id === activeTabId) as Tab;

  const handleTabButtonClick = (id: TabId) => () => {
    setActiveTabId(id);
  };

  return {
    tabs,
    activeTab,
    userRoleQueryResult,
    handleTabButtonClick,
  };
};

export default useStudyRoomPage;
