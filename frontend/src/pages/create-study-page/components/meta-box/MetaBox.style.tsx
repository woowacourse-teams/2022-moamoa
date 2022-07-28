import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const MetaBox = styled.div`
  ${({ theme }) => css`
    background-color: ${theme.colors.white};
    min-width: 255px;
    border: 1px solid #d0d7de;
    box-shadow: 0 1px 1px rgb(0 0 0 / 4%);
    border-radius: 6px;

    & > .title {
      font-size: 16px;
      padding: 8px 12px;
      margin: 0;
      line-height: 1.4;

      border-bottom: 1px solid ${theme.colors.secondary.dark};
    }

    & > .content {
      padding: 8px 12px;
    }
  `}
`;
