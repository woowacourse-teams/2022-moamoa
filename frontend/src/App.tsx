import {
  CreateStudyPage,
  DetailPage,
  ErrorPage,
  LoginRedirectPage,
  MainPage,
  MyStudyPage,
  StudyRoomPage,
} from '@pages';
import { Navigate, Route, Routes } from 'react-router-dom';

import { PATH } from '@constants';

import tw from '@utils/tw';

import { useAuth } from '@hooks/useAuth';

import { Footer, Header, Main } from '@layout';

import EditStudyPage from '@edit-study-page/EditStudyPage';

const App = () => {
  const { isLoggedIn } = useAuth();

  return (
    <div>
      <Header css={tw`fixed top-0 left-0 right-0 z-2`} />
      <Main>
        <Routes>
          <Route path={PATH.MAIN} element={<MainPage />} />
          <Route path={PATH.STUDY_DETAIL()} element={<DetailPage />} />
          <Route
            path={PATH.CREATE_STUDY}
            element={isLoggedIn ? <CreateStudyPage /> : <Navigate to={PATH.MAIN} replace={true} />}
          />
          <Route
            path={PATH.LOGIN}
            element={isLoggedIn ? <Navigate to={PATH.MAIN} replace={true} /> : <LoginRedirectPage />}
          />
          <Route
            path={PATH.MY_STUDY}
            element={isLoggedIn ? <MyStudyPage /> : <Navigate to={PATH.MAIN} replace={true} />}
          />
          <Route
            path={`${PATH.STUDY_ROOM()}/*`}
            element={isLoggedIn ? <StudyRoomPage /> : <Navigate to={PATH.MAIN} replace={true} />}
          />
          <Route
            path={`${PATH.COMMUNITY_ARTICLE()}`}
            element={isLoggedIn ? <StudyRoomPage /> : <Navigate to={PATH.MAIN} replace={true} />}
          />
          <Route
            path={`${PATH.COMMUNITY_PUBLISH()}`}
            element={isLoggedIn ? <StudyRoomPage /> : <Navigate to={PATH.MAIN} replace={true} />}
          />
          <Route
            path={`${PATH.COMMUNITY_EDIT()}`}
            element={isLoggedIn ? <StudyRoomPage /> : <Navigate to={PATH.MAIN} replace={true} />}
          />
          <Route
            path={`${PATH.NOTICE_ARTICLE()}`}
            element={isLoggedIn ? <StudyRoomPage /> : <Navigate to={PATH.MAIN} replace={true} />}
          />
          <Route
            path={`${PATH.NOTICE_PUBLISH()}`}
            element={isLoggedIn ? <StudyRoomPage /> : <Navigate to={PATH.MAIN} replace={true} />}
          />
          <Route
            path={`${PATH.NOTICE_EDIT()}`}
            element={isLoggedIn ? <StudyRoomPage /> : <Navigate to={PATH.MAIN} replace={true} />}
          />
          <Route
            path={PATH.EDIT_STUDY()}
            element={isLoggedIn ? <EditStudyPage /> : <Navigate to={PATH.MAIN} replace={true} />}
          />
          <Route path="*" element={<ErrorPage />} />
        </Routes>
      </Main>
      <Footer marginBottom={'0'} />
    </div>
  );
};

export default App;
