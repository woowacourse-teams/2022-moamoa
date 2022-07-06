import { css } from '@emotion/react';

import MainPage from '@pages/MainPage';

import Header from '@components/Header';
import Wrapper from '@components/Wrapper';

const App = () => {
  return (
    <div
      css={css`
        padding-top: 120px;
      `}
    >
      <Header
        css={css`
          position: fixed;
          top: 0;
          left: 0;
          right: 0;
          z-index: 2;
        `}
      />
      <main>
        <Wrapper>
          <MainPage />
        </Wrapper>
      </main>
    </div>
  );
};

export default App;
