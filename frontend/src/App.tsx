import { BrowserRouter, Route, Routes } from 'react-router-dom';

import { css } from '@emotion/react';

import MainPage from '@pages/MainPage';
import DetailPage from '@pages/detail-page/DetailPage';

import Footer from '@components/Footer';
import Header from '@components/Header';
import Wrapper from '@components/Wrapper';

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
          <Route
            path="/"
            element={
              <Wrapper>
                <MainPage />
              </Wrapper>
            }
          />
          <Route
            path="/study/:studyId"
            element={
              <Wrapper>
                <DetailPage />
              </Wrapper>
            }
          />
        </Routes>
      </main>
      <Footer />
    </BrowserRouter>
  );
};

export default App;
