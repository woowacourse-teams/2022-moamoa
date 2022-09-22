import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

import { noop } from '@utils';

import type { CssLength } from '@custom-types';

import { type ThemeFontSize } from '@styles/theme';

export type BoxButtonProps = {
  variant?: 'primary' | 'secondary';
  children: string | number;
  type: 'submit' | 'button';
  fluid?: boolean;
  disabled?: boolean;
  padding?: `${CssLength} ${CssLength}` | CssLength;
  fontSize?: ThemeFontSize;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const BoxButton: React.FC<BoxButtonProps> = ({
  children,
  type,
  variant = 'primary',
  fluid = true,
  disabled = false,
  padding = '20px 10px',
  fontSize = 'md',
  onClick: handleClick = noop,
}) => {
  return (
    <Self
      type={type}
      fluid={fluid}
      disabled={disabled}
      variant={variant}
      padding={padding}
      fontSize={fontSize}
      onClick={handleClick}
    >
      {children}
    </Self>
  );
};

type StyledBoxButtonProps = Required<Pick<BoxButtonProps, 'disabled' | 'fluid' | 'variant' | 'padding' | 'fontSize'>>;

const applyFilledButtonStyle = (theme: Theme) => css`
  background-color: ${theme.colors.primary.base};
  border: none;

  white-space: nowrap;

  transition: background-color 0.3s ease;

  &:hover {
    background-color: ${theme.colors.primary.light};
  }

  &:active {
    background-color: ${theme.colors.primary.dark};
  }
`;

const applyOutlineButtonStyle = (theme: Theme) => css`
  background-color: ${theme.colors.white};
  border: 1px solid ${theme.colors.primary.base};
  color: ${theme.colors.primary.base};

  transition: border-color 0.3s ease, color 0.3s ease;

  &:hover {
    background-color: ${theme.colors.white};
    border-color: ${theme.colors.primary.light};
    color: ${theme.colors.primary.light};
  }

  &:active {
    background-color: ${theme.colors.white};
    border-color: ${theme.colors.primary.dark};
    color: ${theme.colors.primary.dark};
  }
`;

export const Self = styled.button<StyledBoxButtonProps>`
  ${({ theme, fluid, disabled, variant, padding, fontSize }) => css`
    width: ${fluid ? '100%' : 'auto'};
    padding: ${padding};
    min-height: 30px;

    border-radius: ${theme.radius.sm};
    text-align: center;
    font-size: ${theme.fontSize[fontSize]};
    color: ${theme.colors.white};

    ${applyFilledButtonStyle(theme)}
    ${variant === 'secondary' && applyOutlineButtonStyle(theme)}

    ${disabled &&
    css`
      &:disabled {
        border: none;
        background-color: ${theme.colors.secondary.base};
        color: ${theme.colors.secondary.dark};
      }
    `}
  `}
`;

export default BoxButton;
