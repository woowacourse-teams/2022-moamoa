import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@utils/index';

export const NavButton = styled.button`
  ${({ theme }) => css`
    display: flex;
    justify-content: center;
    align-items: center;
    column-gap: 4px;

    padding: 8px 4px;

    color: ${theme.colors.primary.base};
    border: none;
    background-color: transparent;

    &:hover {
      border-bottom: 1px solid ${theme.colors.secondary.base};
    }

    & > svg {
      fill: ${theme.colors.primary.base};
    }

    & > span {
      color: ${theme.colors.primary.base};
    }
  `}

  ${mqDown('md')} {
    & > span {
      display: none;
    }
  }
`;
