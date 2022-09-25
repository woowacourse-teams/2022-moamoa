import { Suspense, lazy } from 'react';
import { Route, Routes } from 'react-router-dom';

import { PATH } from '@constants';

import { useAuth } from '@hooks/useAuth';

import { Footer, Header, Main } from '@layout';

import RouteWithCondition from '@components/route-with-condition/RouteWithCondition';

const CreateStudyPage = lazy(
  () => import(/* webpackChunkName: "create-study-page" */ '@pages/create-study-page/CreateStudyPage'),
);
const EditStudyPage = lazy(
  () => import(/* webpackChunkName: "create-study-page" */ '@pages/edit-study-page/EditStudyPage'),
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
const NoticeTabPanel = lazy(() => import(/* webpackChunkName: "notice-tab-panel" */ '@notice-tab/NoticeTabPanel'));
const CommunityTabPanel = lazy(
  () => import(/* webpackChunkName: "community-tab-panel" */ '@community-tab/CommunityTabPanel'),
);
const LinkRoomTabPanel = lazy(() => import(/* webpackChunkName: "link-tab-panel" */ '@link-tab/LinkRoomTabPanel'));
const ReviewTabPanel = lazy(() => import(/* webpackChunkName: "review-tab-panel" */ '@review-tab/ReviewTabPanel'));

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
            <Route element={<RouteWithCondition routingCondition={!isLoggedIn} />}>
              <Route path={PATH.LOGIN} element={<LoginRedirectPage />} />
            </Route>
            <Route element={<RouteWithCondition routingCondition={isLoggedIn} />}>
              <Route path={PATH.CREATE_STUDY} element={<CreateStudyPage />} />
              <Route path={PATH.EDIT_STUDY()} element={<EditStudyPage />} />
              <Route path={PATH.MY_STUDY} element={<MyStudyPage />} />
              <Route path={PATH.STUDY_ROOM()} element={<StudyRoomPage />}>
                {/* TODO: 인덱스 페이지를 따로 두면 좋을 것 같다. */}
                <Route index element={<NoticeTabPanel />} />
                <Route path={PATH.NOTICE} element={<NoticeTabPanel />}>
                  {[PATH.NOTICE_PUBLISH, PATH.NOTICE_ARTICLE(), PATH.NOTICE_EDIT()].map((path, index) => (
                    <Route key={index} path={path} element={<NoticeTabPanel />} />
                  ))}
                </Route>
                <Route path={PATH.COMMUNITY} element={<CommunityTabPanel />}>
                  {[PATH.COMMUNITY_PUBLISH, PATH.COMMUNITY_ARTICLE(), PATH.COMMUNITY_EDIT()].map((path, index) => (
                    <Route key={index} path={path} element={<CommunityTabPanel />} />
                  ))}
                </Route>
                <Route path={PATH.LINK} element={<LinkRoomTabPanel />} />
                <Route path={PATH.REVIEW} element={<ReviewTabPanel />} />
                <Route path="*" element={<ErrorPage />} />
              </Route>
            </Route>
            <Route path="*" element={<ErrorPage />} />
          </Routes>
        </Main>
      </Suspense>
      <Footer />
    </>
  );
};

export default App;
