import { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { useGetUserRole } from '@api/member';

export type TabId = string;
export type Tab = { id: TabId; name: string };
export type Tabs = Array<Tab>;

const useStudyRoomPage = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const { studyId: _studyId } = useParams<{ studyId: string }>();
  const studyId = Number(_studyId);

  const userRoleQueryResult = useGetUserRole({ studyId });

  const tabs: Tabs = [
    { id: PATH.NOTICE, name: '공지사항' },
    { id: PATH.COMMUNITY, name: '게시판' },
    { id: PATH.LINK, name: '링크 모음' },
    { id: PATH.REVIEW, name: '후기' },
  ];

  const pathArr = location.pathname.split('/');
  const initialSelectedTabId = pathArr.length >= 4 ? pathArr[3] : tabs[0].id;
  const [activeTabId, setActiveTabId] = useState<TabId>(initialSelectedTabId);

  const activeTab = tabs.find(({ id }) => id === activeTabId) as Tab;

  useEffect(() => {
    const isFirstAccess = pathArr.length < 4;
    if (isFirstAccess) navigate('notice');
    // TODO: study room index page를 만들면 이 useEffect는 필요 없음!
    // 스터디 소개 페이지를 index page로 사용하면 좋을 것 같다.
  }, [pathArr.length]);

  const handleTabButtonClick = (id: TabId) => () => {
    setActiveTabId(id);
  };

  return {
    studyId,
    tabs,
    activeTab,
    userRoleQueryResult,
    handleTabButtonClick,
  };
};

export default useStudyRoomPage;
