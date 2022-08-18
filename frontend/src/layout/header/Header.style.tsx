import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@utils';

export const SearchBarContainer = styled.div`
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  width: 100%;
  max-width: 400px;

  ${mqDown('lg')} {
    position: static;
    left: 0;
    top: 0;
    transform: none;
  }
`;

export const Header = styled.header`
  ${({ theme }) => css`
    display: flex;
    justify-content: space-between;
    align-items: center;
    column-gap: 20px;

    position: relative;

    padding: 20px 40px;

    background-color: ${theme.colors.secondary.light};
    border-bottom: 1px solid ${theme.colors.secondary.dark};

    ${mqDown('md')} {
      padding: 16px 24px;
    }

    ${mqDown('sm')} {
      padding: 10px 12px;
    }
  `}
`;

export const Nav = styled.nav`
  display: flex;
  column-gap: 16px;
`;

export const AvatarContainer = styled.div`
  position: relative;
`;

export const AvatarButton = styled.button`
  border: none;
  background-color: transparent;
`;
