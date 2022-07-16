import { css } from '@emotion/react';

import MainPage from '@pages/MainPage';

import Footer from '@components/Footer';
import Header from '@components/Header';

const App = () => {
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
      <main
        css={css`
          padding: 120px 0 80px;
          min-height: calc(100vh - 80px);
        `}
      >
        <MainPage />
      </main>
      <Footer />
    </div>
  );
};

export default App;
