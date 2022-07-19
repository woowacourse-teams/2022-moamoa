import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const StudyFloatBox = styled.div`
  ${({ theme }) => css`
    padding: 40px 40px 30px 40px;

    background: ${theme.colors.white};
    border: 3px solid ${theme.colors.primary.base};
    box-shadow: 8px 8px 0px ${theme.colors.secondary.dark};
    border-radius: 25px;

    font-size: 24px;

    & > .top {
      margin-bottom: 30px;

      & > .deadline {
        margin-bottom: 20px;
        strong {
          font-size: 28px;
        }
      }

      & > .seating-capacity {
        display: flex;
        justify-content: space-between;
        margin-bottom: 20px;
      }

      & > .owner {
        display: flex;
        justify-content: space-between;
        margin-bottom: 20px;
      }
    }
  `}
`;
