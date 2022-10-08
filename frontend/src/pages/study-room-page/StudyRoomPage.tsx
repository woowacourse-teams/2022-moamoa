import { Navigate, Outlet } from 'react-router-dom';

import { PATH } from '@constants';

import Flex from '@components/flex/Flex';
import PageWrapper from '@components/page-wrapper/PageWrapper';

import SideMenu from '@study-room-page/components/side-menu/SideMenu';
import useStudyRoomPage from '@study-room-page/hooks/useStudyRoomPage';

const StudyRoomPage: React.FC = () => {
  const { tabs, activeTabId, userRoleQueryResult, handleTabButtonClick } = useStudyRoomPage();
  const { data, isError, isSuccess } = userRoleQueryResult;

  if (isSuccess && data.role === 'NON_MEMBER') {
    alert('잘못된 접근입니다.');
    return <Navigate to={PATH.MAIN} replace={true} />;
  }

  if (isError) {
    alert('오류가 발생했습니다.');
    return <Navigate to={PATH.MAIN} replace={true} />;
  }

  return (
    <PageWrapper>
      <Flex alignItems="flex-start">
        <SideMenu activeTabId={activeTabId} tabs={tabs} onTabButtonClick={handleTabButtonClick} />
        <Flex.Item flexGrow={1}>
          <Outlet />
        </Flex.Item>
      </Flex>
    </PageWrapper>
  );
};

export default StudyRoomPage;
