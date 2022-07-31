import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const ReviewForm = styled.form`
  ${({ theme }) => css`
    display: flex;
    column-gap: 15px;

    & > .left {
      flex: 1;

      display: flex;
      flex-direction: column;
      row-gap: 10px;

      & > .top {
        display: flex;
        justify-content: space-between;
        align-items: flex-end;
      }

      & > .bottom {
        height: 80px;
      }

      textarea {
        width: 100%;
        height: 100%;
        padding: 8px;
        font-size: 18px;
      }
    }

    & > .right {
      display: flex;
      align-items: flex-end;

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
