import { useState } from 'react';
import { useLocation, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { useGetUserRole } from '@api/member';

export type TabId = typeof PATH[keyof Pick<typeof PATH, 'NOTICE' | 'COMMUNITY' | 'LINK' | 'REVIEW'>];
export type Tab = { id: TabId; name: string };
export type Tabs = Array<Tab>;

const useStudyRoomPage = () => {
  const location = useLocation();

  const { studyId: _studyId } = useParams<{ studyId: string }>();
  const studyId = Number(_studyId);

  const userRoleQueryResult = useGetUserRole({ studyId });

  const tabs: Tabs = [
    { id: PATH.NOTICE, name: '공지사항' },
    { id: PATH.COMMUNITY, name: '게시판' },
    { id: PATH.LINK, name: '링크 모음' },
    { id: PATH.REVIEW, name: '후기' },
  ];
  const tabIds = tabs.map(tab => tab.id);

  const paths = location.pathname.split('/');
  const initialSelectedTabId = tabIds.find(tabId => paths.includes(tabId)) ?? tabs[0].id;
  const [activeTabId, setActiveTabId] = useState<TabId>(initialSelectedTabId);

  const handleTabButtonClick = (id: TabId) => () => {
    setActiveTabId(id);
  };

  return {
    studyId,
    tabs,
    activeTabId,
    userRoleQueryResult,
    handleTabButtonClick,
  };
};

export default useStudyRoomPage;
