import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { noop } from '@utils';

import { mqDown } from '@styles/responsive';

export type NavButtonProps = {
  children: React.ReactNode;
  ariaLabel: string;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const NavButton: React.FC<NavButtonProps> = ({ children, ariaLabel, onClick: handleClick = noop }) => {
  return (
    <Self aria-label={ariaLabel} onClick={handleClick}>
      {children}
    </Self>
  );
};

export default NavButton;

const Self = styled.button`
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
