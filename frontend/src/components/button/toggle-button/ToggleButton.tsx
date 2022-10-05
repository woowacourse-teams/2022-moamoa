import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

import { CustomCSS, resolveCustomCSS } from '@styles/custom-css';
import { mqDown } from '@styles/responsive';

export type ToggleButtonProps = {
  children: React.ReactNode;
  variant?: 'primary' | 'secondary';
  fluid?: boolean;
  checked: boolean;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
  custom?: CustomCSS<'fontSize'>;
};

const ToggleButton: React.FC<ToggleButtonProps> = ({
  children,
  checked,
  fluid = false,
  variant = 'primary',
  onClick: handleClick,
  custom,
}) => {
  return (
    <Self
      type="button"
      fluid={fluid}
      checked={checked}
      onClick={handleClick}
      variant={variant}
      css={resolveCustomCSS(custom)}
    >
      {children}
    </Self>
  );
};

type StyledToggleButtonProps = Required<Pick<ToggleButtonProps, 'checked' | 'fluid' | 'variant'>>;

const applyCheckedStyle = (theme: Theme) => css`
  color: ${theme.colors.primary.base};
  border-bottom: 2px solid ${theme.colors.primary.base};

  &:hover,
  &:active {
    color: ${theme.colors.primary.base};
    border-bottom: 2px solid ${theme.colors.primary.base};
  }
`;

export const Self = styled.button<StyledToggleButtonProps>`
  ${({ theme, checked, fluid, variant }) => css`
    display: flex;
    justify-content: center;
    align-items: center;
    column-gap: 4px;

    width: ${fluid ? '100%' : 'auto'};
    padding: 8px 4px;

    color: ${theme.colors.primary.base};
    font-size: ${theme.fontSize.md};
    border: none;
    border-bottom: 2px solid transparent;
    background-color: transparent;
    transition: border-bottom 0.2s ease;

    ${variant === 'secondary' && `color: ${theme.colors.secondary.dark};`}

    &:hover {
      border-bottom: 2px solid ${theme.colors.secondary.base};
    }

    &:active {
      border-bottom: 2px solid ${theme.colors.secondary.dark};
    }

    & > svg {
      stroke: ${theme.colors.primary.base};
      fill: ${theme.colors.primary.base};
    }

    & * {
      color: ${theme.colors.primary.base};
    }

    ${checked && applyCheckedStyle(theme)}
  `}

  ${mqDown('md')} {
    & > span {
      display: none;
    }
  }
`;

export default ToggleButton;
