import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown, mqUp } from '@utils';

export const Sidebar = styled.nav`
  ${({ theme }) => css`
    width: 100%;
    max-width: 180px;
    padding: 16px;

    background-color: ${theme.colors.secondary.light};
    text-align: center;

    ${mqDown('lg')} {
      display: none;
    }
  `}
`;

export const Bottombar = styled.nav`
  ${({ theme }) => css`
    display: flex;
    align-items: space-between;
    column-gap: 16px;

    position: fixed;
    left: 0;
    bottom: 0;
    z-index: 1;

    width: 100%;
    padding: 16px;

    background-color: ${theme.colors.white};
    text-align: center;
    border-top: 1px solid ${theme.colors.secondary.base};

    ${mqUp('lg')} {
      display: none;
    }
  `}
`;
