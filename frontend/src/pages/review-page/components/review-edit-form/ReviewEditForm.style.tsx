import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { LetterCounter } from '@components/letter-counter/LetterCounter.style';

export const ReviewEditForm = styled.form`
  ${({ theme }) => css`
    display: flex;
    column-gap: 15px;

    & > .left {
      flex: 1;

      display: flex;
      flex-direction: column;
      row-gap: 10px;

      & > .top {
        position: relative;

        height: 80px;
        textarea {
          width: 100%;
          height: 100%;
          padding: 8px 8px 20px 8px;
          font-size: 18px;
        }

        ${LetterCounter} {
          position: absolute;
          right: 4px;
          bottom: 2px;
        }
      }

      & > .bottom {
        display: flex;
        justify-content: flex-end;
        button {
          background-color: transparent;
          color: ${theme.colors.secondary.dark};
          border: none;
          outline: none;

          font-size: 16px;
        }
      }
    }

    & > .right {
      display: flex;
      align-items: flex-start;

      button {
        padding: 0 25px;
        font-size: 24px;
        color: white;
        background-color: ${theme.colors.primary.dark};
        height: 80px;
        border: none;
        outline: none;
      }
    }
  `}
`;
