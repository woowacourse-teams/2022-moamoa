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

        /* @font-face {
          font-family: 'NanumSquareRound';
          font-weight: 300;
          src: url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundL.eot);
          src: url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundL.eot?#iefix)
              format('embedded-opentype'),
            url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundL.woff2)
              format('woff2'),
            url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundL.woff)
              format('woff'),
            url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundL.ttf)
              format('truetype');
        }

        @font-face {
          font-family: 'NanumSquareRound';
          font-weight: 400;
          src: url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundR.eot);
          src: url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundR.eot?#iefix)
              format('embedded-opentype'),
            url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundR.woff2)
              format('woff2'),
            url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundR.woff)
              format('woff'),
            url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundR.ttf)
              format('truetype');
        }

        @font-face {
          font-family: 'NanumSquareRound';
          font-weight: 700;
          src: url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundB.eot);
          src: url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundB.eot?#iefix)
              format('embedded-opentype'),
            url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundB.woff2)
              format('woff2'),
            url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundB.woff)
              format('woff'),
            url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundB.ttf)
              format('truetype');
        }

        @font-face {
          font-family: 'NanumSquareRound';
          font-weight: 800;
          src: url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundEB.eot);
          src: url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundEB.eot?#iefix)
              format('embedded-opentype'),
            url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundEB.woff2)
              format('woff2'),
            url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundEB.woff)
              format('woff'),
            url(https://hangeul.pstatic.net/hangeul_static/webfont/NanumSquareRound/NanumSquareRoundEB.ttf)
              format('truetype');
        } */

        @font-face {
          font-family: 'Pretendard';
          src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Thin.woff') format('woff');
          font-weight: 100;
          font-style: normal;
        }

        @font-face {
          font-family: 'Pretendard';
          src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-ExtraLight.woff')
            format('woff');
          font-weight: 200;
          font-style: normal;
        }

        @font-face {
          font-family: 'Pretendard';
          src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Light.woff') format('woff');
          font-weight: 300;
          font-style: normal;
        }

        @font-face {
          font-family: 'Pretendard';
          src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff')
            format('woff');
          font-weight: 400;
          font-style: normal;
        }

        @font-face {
          font-family: 'Pretendard';
          src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Medium.woff')
            format('woff');
          font-weight: 500;
          font-style: normal;
        }

        @font-face {
          font-family: 'Pretendard';
          src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-SemiBold.woff')
            format('woff');
          font-weight: 600;
          font-style: normal;
        }

        @font-face {
          font-family: 'Pretendard';
          src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Bold.woff') format('woff');
          font-weight: 700;
          font-style: normal;
        }

        @font-face {
          font-family: 'Pretendard';
          src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-ExtraBold.woff')
            format('woff');
          font-weight: 800;
          font-style: normal;
        }

        @font-face {
          font-family: 'Pretendard';
          src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Black.woff') format('woff');
          font-weight: 900;
          font-style: normal;
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

        input {
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
          border-radius: 4px;
        }
      `}
    />
  );
};

export default GlobalStyles;
