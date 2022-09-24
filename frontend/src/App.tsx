import NoticeTabPanel from '@notice-tab/NoticeTabPanel';
import { Route, Routes } from 'react-router-dom';

import { PATH } from '@constants';

import { useAuth } from '@hooks/useAuth';

import { Footer, Header, Main } from '@layout';

import {
  CreateStudyPage,
  DetailPage,
  EditStudyPage,
  ErrorPage,
  LoginRedirectPage,
  MainPage,
  MyStudyPage,
  StudyRoomPage,
} from '@pages';

import RouteWithCondition from '@components/route-with-condition/RouteWithCondition';

import LinkRoomTabPanel from '@study-room-page/tabs/link-room-tab-panel/LinkRoomTabPanel';
import ReviewTabPanel from '@study-room-page/tabs/review-tab-panel/ReviewTabPanel';

import CommunityTabPanel from '@community-tab/CommunityTabPanel';

const App = () => {
  const { isLoggedIn } = useAuth();

  return (
    <div>
      <Header />
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
      <Footer />
    </div>
  );
};

export default App;
