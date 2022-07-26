import { useContext } from 'react';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';

import { css } from '@emotion/react';

import { PATH } from '@constants';

import { LoginContext } from '@context/login/LoginProvider';

import Footer from '@layout/footer/Footer';
import Header from '@layout/header/Header';
import Main from '@layout/main/Main';

import ErrorPage from '@pages/error-page/ErrorPage';
import LoginRedirectPage from '@pages/login-redirect-page/LoginRedirectPage';
import MainPage from '@pages/main-page/MainPage';

import DetailPage from '@detail-page/DetailPage';

const App = () => {
  const { isLoggedIn } = useContext(LoginContext);

  return (
    <BrowserRouter>
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
          <Route path={PATH.LOGIN} element={isLoggedIn ? <Navigate to="/" replace={true} /> : <LoginRedirectPage />} />
          <Route path="*" element={<ErrorPage />} />
        </Routes>
      </Main>
      <Footer />
    </BrowserRouter>
  );
};

export default App;
