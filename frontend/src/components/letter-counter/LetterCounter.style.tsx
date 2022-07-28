import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.div`
  ${({ theme }) => css`
    font-size: 12px;
    color: ${theme.colors.secondary.dark};

    & > span {
      color: ${theme.colors.secondary.dark};
    }
  `}
`;
