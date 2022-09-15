import { Navigate } from 'react-router-dom';

import { PATH } from '@constants';

import tw from '@utils/tw';

import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@study-room-page/StudyRoomPage.style';
import useStudyRoomPage from '@study-room-page/hooks/useStudyRoomPage';
import SideMenu from '@study-room-page/tabs/side-menu/SideMenu';

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
          css={tw`sticky top-100 left-0 z-1 self-start`}
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
