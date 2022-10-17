import { Outlet } from 'react-router-dom';

import styled from '@emotion/styled';

import { mqDown } from '@utils';

import Flex from '@components/flex/Flex';
import Wrapper from '@components/wrapper/Wrapper';

import SideMenu from '@study-room-page/components/side-menu/SideMenu';
import useStudyRoomPage from '@study-room-page/hooks/useStudyRoomPage';

const StudyRoomPage: React.FC = () => {
  const { tabs, activeTabId, handleTabButtonClick } = useStudyRoomPage();

  return (
    <Wrapper>
      <Flex alignItems="flex-start" gap="40px">
        <SideMenu activeTabId={activeTabId} tabs={tabs} onTabButtonClick={handleTabButtonClick} />
        <MainSection>
          <Outlet />
        </MainSection>
      </Flex>
    </Wrapper>
  );
};

export default StudyRoomPage;

const sidebarWidth = 180;
const MainSection = styled.section`
  flex-grow: 1;

  max-width: calc(100% - ${sidebarWidth}px);

  ${mqDown('lg')} {
    max-width: 100%;
  }
`;
