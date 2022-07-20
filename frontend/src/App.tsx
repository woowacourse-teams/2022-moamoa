import { useContext } from 'react';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';

import { css } from '@emotion/react';

import { LoginContext } from '@context/login/LoginProvider';

import Footer from '@layout/footer/Footer';
import Header from '@layout/header/Header';

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
      <main
        css={css`
          padding: 120px 0 80px;
          min-height: calc(100vh - 80px);
        `}
      >
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/study/:studyId" element={<DetailPage />} />
          <Route path="/login" element={isLoggedIn ? <Navigate to="/" replace={true} /> : <LoginRedirectPage />} />
          <Route path="*" element={<div>에러 페이지</div>} />
        </Routes>
      </main>
      <Footer />
    </BrowserRouter>
  );
};

export default App;
