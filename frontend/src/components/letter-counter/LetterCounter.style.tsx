import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const LetterCounter = styled.div`
  ${({ theme }) => css`
    font-size: 12px;
    color: ${theme.colors.secondary.dark};
    & > span {
      color: ${theme.colors.secondary.dark};
    }
    & > span:first-of-type {
      color: ${theme.colors.black};
    }
  `}
`;
