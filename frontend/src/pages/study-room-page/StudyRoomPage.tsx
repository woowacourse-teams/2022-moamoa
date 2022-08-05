import { Navigate } from 'react-router-dom';

import { css } from '@emotion/react';

import { PATH } from '@constants';

import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@study-room-page/StudyRoomPage.style';
import SideMenu from '@study-room-page/components/side-menu/SideMenu';
import useStudyRoomPage from '@study-room-page/hooks/useStudyRoomPage';

const StudyRoomPage: React.FC = () => {
  const { tabs, activeTab, userRoleQueryResult, handleTabButtonClick } = useStudyRoomPage();
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
