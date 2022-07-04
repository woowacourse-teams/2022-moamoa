import { Global, css } from '@emotion/react';

import { theme } from './theme';

const GlobalStyles = () => {
  return (
    <Global
      styles={css`
        *,
        *::before,
        *::after {
          box-sizing: border-box;
          padding: 0;
          margin: 0;
          color: ${theme.colors.black};
        }

        @font-face {
          font-family: 'NanumSquareRound';
          src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/NanumSquareRound.woff') format('woff');
          font-weight: normal;
          font-style: normal;
        }

        body {
          font-family: 'NanumSquareRound', -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu',
            'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue', sans-serif;
          background-color: ${theme.colors.secondary.light};
        }

        code {
          font-family: source-code-pro, Menlo, Monaco, Consolas, 'Courier New', monospace;
        }

        li {
          list-style: none;
        }

        button {
          cursor: pointer;
        }

        a {
          text-decoration: none;
        }
      `}
    />
  );
};

export default GlobalStyles;
