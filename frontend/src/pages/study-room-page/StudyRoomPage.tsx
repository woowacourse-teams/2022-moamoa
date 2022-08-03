import { css } from '@emotion/react';

import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@study-room-page/StudyRoomPage.style';
import SideMenu from '@study-room-page/components/side-menu/SideMenu';
import useStudyRoomPage from '@study-room-page/hooks/useStudyRoomPage';

const StudyRoomPage: React.FC = () => {
  const { tabs, activeTab, handleTabButtonClick } = useStudyRoomPage();

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
          activeTabId={activeTab.id}
          tabs={tabs}
          onTabButtonClick={handleTabButtonClick}
        />
        <S.Content>{activeTab.content}</S.Content>
      </S.Container>
    </Wrapper>
  );
};

export default StudyRoomPage;
