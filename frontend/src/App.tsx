import { Suspense, lazy } from 'react';
import { Navigate, Route, Routes } from 'react-router-dom';

import { PATH } from '@constants';

import { useAuth } from '@hooks/useAuth';

import { Footer, Header, Main } from '@layout';

import EditStudyPage from '@edit-study-page/EditStudyPage';

const CreateStudyPage = lazy(
  () => import(/* webpackChunkName: "create-study-page" */ '@pages/create-study-page/CreateStudyPage'),
);
const DetailPage = lazy(() => import(/* webpackChunkName: "detail-page" */ '@pages/detail-page/DetailPage'));
const ErrorPage = lazy(() => import(/* webpackChunkName: "error-page" */ '@pages/error-page/ErrorPage'));
const LoginRedirectPage = lazy(
  () => import(/* webpackChunkName: "login-redirect-page" */ '@pages/login-redirect-page/LoginRedirectPage'),
);
const MainPage = lazy(() => import(/* webpackChunkName: "main-page" */ '@pages/main-page/MainPage'));
const MyStudyPage = lazy(() => import(/* webpackChunkName: "my-study-page" */ '@pages/my-study-page/MyStudyPage'));
const StudyRoomPage = lazy(
  () => import(/* webpackChunkName: "study-room-page" */ '@pages/study-room-page/StudyRoomPage'),
);

const App = () => {
  const { isLoggedIn } = useAuth();

  return (
    <>
      <Header />
      <Suspense fallback={<></>}>
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
      </Suspense>
      <Footer />
    </>
  );
};

export default App;
