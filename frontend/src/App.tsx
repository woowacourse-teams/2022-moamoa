import { Suspense, lazy } from 'react';
import { Navigate, Route, Routes } from 'react-router-dom';

import { PATH } from '@constants';

import { useAuth } from '@hooks/useAuth';

import { Footer, Header, Main } from '@layout';

import RouteWithCondition from '@shared/route-with-condition/RouteWithCondition';

// TODO: Outlet과 Route를 잘 사용하는 방법이 없을까?
// Routes는 무조건 Route만 children으로 받을 수 있고 그 반대도 그렇다.
// Routes를 2번 이상 사용하면 첫번째 Routes만 인식한다.

const CreateStudyPage = lazy(
  () => import(/* webpackChunkName: "create-study-page" */ '@create-study-page/CreateStudyPage'),
);
const EditStudyPage = lazy(() => import(/* webpackChunkName: "edit-study-page" */ '@edit-study-page/EditStudyPage'));
const DetailPage = lazy(() => import(/* webpackChunkName: "detail-page" */ '@detail-page/DetailPage'));
const ErrorPage = lazy(() => import(/* webpackChunkName: "error-page" */ '@error-page/ErrorPage'));
const LoginRedirectPage = lazy(
  () => import(/* webpackChunkName: "login-redirect-page" */ '@login-redirect-page/LoginRedirectPage'),
);
const MainPage = lazy(() => import(/* webpackChunkName: "main-page" */ '@main-page/MainPage'));
const MyStudyPage = lazy(() => import(/* webpackChunkName: "my-study-page" */ '@my-study-page/MyStudyPage'));
const DraftPage = lazy(() => import(/* webpackChunkName: "draft-page" */ '@draft-page/DraftPage'));
const StudyRoomPage = lazy(() => import(/* webpackChunkName: "study-room-page" */ '@study-room-page/StudyRoomPage'));

const NoticeTabPanel = lazy(() => import(/* webpackChunkName: "notice-tab-panel" */ '@notice-tab/NoticeTabPanel'));

const CommunityTabPanel = lazy(
  () => import(/* webpackChunkName: "community-tab-panel" */ '@community-tab/CommunityTabPanel'),
);
const CommunityArticleList = lazy(
  () =>
    import(
      /* webpackChunkName: "community-article-list" */ '@community-tab/components/article-list-page/ArticleListPage'
    ),
);
const CommunityArticle = lazy(
  () => import(/* webpackChunkName: "community-article" */ '@community-tab/components/article/Article'),
);
const CommunityPublish = lazy(
  () => import(/* webpackChunkName: "community-publish" */ '@community-tab/components/publish/Publish'),
);
const CommunityEdit = lazy(
  () => import(/* webpackChunkName: "community-edit" */ '@community-tab/components/edit/Edit'),
);
const DraftArticlePublish = lazy(
  () =>
    import(
      /* webpackChunkName: "community-draft-article-publish" */ '@community-tab/inner-pages/draft-article-publish/DraftArticlePublish'
    ),
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
              <Route path={PATH.DRAFT} element={<DraftPage />} />
            </Route>
            <Route path={PATH.STUDY_ROOM()} element={<StudyRoomPage />}>
              {/* TODO: 인덱스 페이지(HOME)를 따로 두면 좋을 것 같다. */}
              <Route index element={<Navigate to={PATH.NOTICE} replace />} />
              <Route path={PATH.NOTICE} element={<NoticeTabPanel />}>
                {[PATH.NOTICE_PUBLISH, PATH.NOTICE_ARTICLE(), PATH.NOTICE_EDIT()].map((path, index) => (
                  <Route key={index} path={path} element={<NoticeTabPanel />} />
                ))}
              </Route>
              <Route path={PATH.COMMUNITY} element={<CommunityTabPanel />}>
                <Route index element={<CommunityArticleList />} />
                <Route path={PATH.COMMUNITY_PUBLISH} element={<CommunityPublish />} />
                <Route path={PATH.COMMUNITY_ARTICLE()} element={<CommunityArticle />} />
                <Route path={PATH.COMMUNITY_EDIT()} element={<CommunityEdit />} />
                <Route path={PATH.DRAFT_COMMUNITY_PUBLISH()} element={<DraftArticlePublish />} />
              </Route>
              <Route path={PATH.LINK} element={<LinkRoomTabPanel />} />
              <Route path={PATH.REVIEW} element={<ReviewTabPanel />} />
              <Route path="*" element={<ErrorPage />} />
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
