import { useContext } from 'react';
import { Navigate, Route, Routes, useLocation } from 'react-router-dom';

import { css } from '@emotion/react';

import { PATH } from '@constants';

import { LoginContext } from '@context/login/LoginProvider';

import Footer from '@layout/footer/Footer';
import Header from '@layout/header/Header';
import Main from '@layout/main/Main';

import CreateStudyPage from '@pages/create-study-page/CreateStudyPage';
import ErrorPage from '@pages/error-page/ErrorPage';
import LoginRedirectPage from '@pages/login-redirect-page/LoginRedirectPage';
import MainPage from '@pages/main-page/MainPage';
import MyStudyPage from '@pages/my-study-page/MyStudyPage';

import DetailPage from '@detail-page/DetailPage';

const App = () => {
  const { isLoggedIn } = useContext(LoginContext);

  return (
    <div>
      <Header
        css={css`
          position: fixed;
          top: 0;
          left: 0;
          right: 0;
          z-index: 2;
        `}
      />
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
          <Route path={PATH.MY_STUDY} element={isLoggedIn ? <MyStudyPage /> : <Navigate to="/" replace={true} />} />
          <Route path="*" element={<ErrorPage />} />
        </Routes>
      </Main>
      <Footer marginBottom={'0'} />
    </div>
  );
};

export default App;
