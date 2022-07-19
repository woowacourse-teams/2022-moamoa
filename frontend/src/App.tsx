import { BrowserRouter, Route, Routes } from 'react-router-dom';

import { css } from '@emotion/react';

import Footer from '@layout/footer/Footer';
import Header from '@layout/header/Header';

import MainPage from '@pages/main-page/MainPage';

import DetailPage from '@detail-page/DetailPage';

const App = () => {
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
        </Routes>
      </main>
      <Footer />
    </BrowserRouter>
  );
};

export default App;
