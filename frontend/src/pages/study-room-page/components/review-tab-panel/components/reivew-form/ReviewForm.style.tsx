import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const ReviewForm = styled.form`
  ${({ theme }) => css`
    .textarea-container {
      border: 1px solid ${theme.colors.secondary.dark};
      border-radius: 4px;

      & > .top {
        padding-top: 12px;
        padding-left: 14px;
        .user-info {
          display: flex;
          align-items: center;
          column-gap: 6px;
          .username {
            font-weight: 900;
          }
        }
      }
      & > .middle {
        padding: 10px 14px;
        textarea {
          min-height: 90px;
          height: 100%;
          width: 100%;
          font-size: 18px;
          padding: 6px;
          border-color: transparent;

          background-color: transparent;
        }
      }
      & > .bottom {
        display: flex;
        justify-content: space-between;
        align-items: center;

        padding: 6px 14px;
        border-top: 1px solid ${theme.colors.secondary.base};

        .register-btn {
          border-radius: 4px;
          padding: 8px 10px;
          font-size: 12px;
        }
      }
    }
  `}
`;
