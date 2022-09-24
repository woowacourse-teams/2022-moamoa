import NoticeTabPanel from '@notice-tab/NoticeTabPanel';
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
import { Navigate, Route, Routes } from 'react-router-dom';

import { PATH } from '@constants';

import { useAuth } from '@hooks/useAuth';

import { Footer, Header, Main } from '@layout';

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
          <Route
            path={PATH.CREATE_STUDY}
            element={isLoggedIn ? <CreateStudyPage /> : <Navigate to={PATH.MAIN} replace={true} />}
          />
          <Route
            path={PATH.EDIT_STUDY()}
            element={isLoggedIn ? <EditStudyPage /> : <Navigate to={PATH.MAIN} replace={true} />}
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
            path={PATH.STUDY_ROOM()}
            element={isLoggedIn ? <StudyRoomPage /> : <Navigate to={PATH.MAIN} replace={true} />}
          >
            <Route index element={<NoticeTabPanel />} />
            <Route path={PATH.NOTICE} element={<NoticeTabPanel />}>
              <Route path={PATH.NOTICE_PUBLISH} element={<NoticeTabPanel />} />
              <Route path={PATH.NOTICE_ARTICLE()} element={<NoticeTabPanel />} />
              <Route path={PATH.NOTICE_EDIT()} element={<NoticeTabPanel />} />
            </Route>
            <Route path={PATH.COMMUNITY} element={<CommunityTabPanel />}>
              <Route path={PATH.COMMUNITY_PUBLISH} element={<CommunityTabPanel />} />
              <Route path={PATH.COMMUNITY_ARTICLE()} element={<CommunityTabPanel />} />
              <Route path={PATH.COMMUNITY_EDIT()} element={<CommunityTabPanel />} />
            </Route>
            <Route path={PATH.LINK} element={<LinkRoomTabPanel />} />
            <Route path={PATH.REVIEW} element={<ReviewTabPanel />} />
            <Route path="*" element={<ErrorPage />} />
          </Route>
          <Route path="*" element={<ErrorPage />} />
        </Routes>
      </Main>
      <Footer />
    </div>
  );
};

export default App;
