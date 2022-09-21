import { Global, css } from '@emotion/react';

import rocketCursorPointerImage from '@assets/images/rocket-cursor-pointer.png';
import rocketCursorImage from '@assets/images/rocket-cursor.png';

import { theme } from '@styles/theme';

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
          font-family: 'Pretendard';
          src: url('static/Pretendard-Light.woff2') format('woff');
          font-weight: ${theme.fontWeight.light};
          font-style: normal;
          font-display: swap;
        }

        @font-face {
          font-family: 'Pretendard';
          src: url('static/Pretendard-Regular.woff2') format('woff');
          font-weight: ${theme.fontWeight.normal};
          font-style: normal;
          font-display: swap;
        }

        @font-face {
          font-family: 'Pretendard';
          src: url('static/Pretendard-SemiBold.woff2') format('woff');
          font-weight: ${theme.fontWeight.bold};
          font-style: normal;
          font-display: swap;
        }

        @font-face {
          font-family: 'Pretendard';
          src: url('static/Pretendard-ExtraBold.woff2') format('woff');
          font-weight: ${theme.fontWeight.bolder};
          font-style: normal;
          font-display: swap;
        }

        body {
          font-family: 'Pretendard', -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu',
            'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue', sans-serif;
          background-color: ${theme.colors.secondary.light};
        }

        code {
          font-family: source-code-pro, Menlo, Monaco, Consolas, 'Courier New', monospace;
        }

        button,
        input,
        textarea,
        select,
        option {
          font-family: inherit;
        }

        body,
        label {
          cursor: url(${rocketCursorImage}) 12 7, auto;
        }

        h1 {
          font-weight: ${theme.fontWeight.bolder};
        }
        h2,
        h3,
        h4,
        h5 {
          font-weight: ${theme.fontWeight.bold};
        }

        button,
        a,
        select,
        input[type='checkbox'],
        input[type='date'],
        option {
          cursor: url(${rocketCursorPointerImage}) 12 7, pointer;
        }

        button {
          &:disabled {
            cursor: url(${rocketCursorImage}) 12 7, default;
          }
        }

        a {
          text-decoration: none;
        }

        textarea {
          resize: none;
        }

        input,
        textarea {
          background-color: ${theme.colors.white};
          &::placeholder {
            color: ${theme.colors.secondary.dark};
          }
        }

        li {
          list-style: none;
        }

        select,
        textarea,
        input {
          border-radius: ${theme.radius.xs};
        }
      `}
    />
  );
};

export default GlobalStyles;
