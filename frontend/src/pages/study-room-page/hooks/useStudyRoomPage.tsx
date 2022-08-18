import { useState } from 'react';
import { useParams } from 'react-router-dom';

import { useGetUserRole } from '@api/member';

import CommunityTabPanel from '@study-room-page/tabs/community-tab-panel/CommunityTabPanel';
import LinkRoomTabPanel from '@study-room-page/tabs/link-room-tab-panel/LinkRoomTabPanel';
import ReviewTabPanel from '@study-room-page/tabs/review-tab-panel/ReviewTabPanel';

export type TabId = 'notice' | 'community' | 'link-room' | 'review';

export type Tab = { id: TabId; name: string; content: React.ReactNode };

export type Tabs = Array<Tab>;

const useStudyRoomPage = () => {
  // const { studyId: _studyId } = useParams() as { studyId: string };
  // const studyId = Number(_studyId);
  const studyId = 1;

  const userRoleQueryResult = useGetUserRole({ studyId: Number(studyId) });

  const tabs: Tabs = [
    { id: 'notice', name: '공지사항', content: '공지사항입니다.' },
    { id: 'community', name: '게시판', content: <CommunityTabPanel studyId={studyId} /> },
    { id: 'link-room', name: '링크 모음', content: <LinkRoomTabPanel /> },
    { id: 'review', name: '후기', content: <ReviewTabPanel studyId={studyId} /> },
  ];

  const [activeTabId, setActiveTabId] = useState<TabId>(tabs[1].id);

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
