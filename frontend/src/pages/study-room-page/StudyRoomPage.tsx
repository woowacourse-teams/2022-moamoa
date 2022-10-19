import { Outlet } from 'react-router-dom';

import styled from '@emotion/styled';

import { mqDown } from '@styles/responsive';

import Flex from '@shared/flex/Flex';
import PageWrapper from '@shared/page-wrapper/PageWrapper';

import SideMenu from '@study-room-page/components/side-menu/SideMenu';
import useStudyRoomPage from '@study-room-page/hooks/useStudyRoomPage';

const StudyRoomPage: React.FC = () => {
  const { tabs, activeTabId, handleTabButtonClick } = useStudyRoomPage();

  return (
    <PageWrapper>
      <Flex alignItems="flex-start" columnGap={`${gap}px`}>
        <SideMenu activeTabId={activeTabId} tabs={tabs} onTabButtonClick={handleTabButtonClick} />
        <MainSection>
          <Outlet />
        </MainSection>
      </Flex>
    </PageWrapper>
  );
};

export default StudyRoomPage;

const sidebarWidth = 180;
const gap = 40;
const MainSection = styled.section`
  flex-grow: 1;

  max-width: calc(100% - ${sidebarWidth + gap}px);

  ${mqDown('lg')} {
    max-width: 100%;
  }
`;
