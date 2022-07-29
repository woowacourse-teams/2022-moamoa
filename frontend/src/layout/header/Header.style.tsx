import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@utils/index';

import * as Logo from '@layout/header/logo/Logo.style';
import * as SearchBar from '@layout/header/search-bar/SearchBar.style';

export const SearchBarContainer = styled.div`
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  width: 100%;
  max-width: 400px;
`;

export const Row = styled.header`
  ${({ theme }) => css`
    display: flex;
    justify-content: space-between;
    align-items: center;
    column-gap: 20px;

    position: relative;

    padding: 20px 40px;

    background-color: ${theme.colors.secondary.light};
    border-bottom: 1px solid ${theme.colors.secondary.dark};

    ${mqDown('lg')} {
      ${Logo.ImageContainer} {
        margin-right: 0;
      }
      ${Logo.BorderText} {
        display: none;
      }
      ${SearchBarContainer} {
        position: static;
        left: 0;
        top: 0;
        transform: none;
      }
    }

    ${mqDown('md')} {
      padding: 16px 24px;
      ${SearchBar.Input} {
        font-size: 18px;
      }
    }

    ${mqDown('sm')} {
      padding: 10px 12px;
      ${SearchBar.Input} {
        font-size: 16px;
      }
    }
  `}
`;

export const Nav = styled.nav`
  display: flex;
`;

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
