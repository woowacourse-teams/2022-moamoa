import { type ReactSVG } from 'react';

import { type Theme, css, useTheme } from '@emotion/react';
import styled from '@emotion/styled';

import { type MakeRequired } from '@custom-types';

import { type CustomCSS, resolveCustomCSS } from '@styles/custom-css';

export type IconButtonProps = {
  children: React.ReactElement<ReactSVG>;
  ariaLabel: string;
  variant?: 'primary' | 'secondary';
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
  custom?: CustomCSS<'width' | 'height' | 'fontSize' | 'borderRadius' | 'transition' | 'backgroundColor'>;
};

const IconButton: React.FC<IconButtonProps> = ({
  custom,
  children,
  ariaLabel,
  variant = 'primary',
  onClick: handleClick,
}) => {
  const theme = useTheme();
  return (
    <Self
      css={resolveCustomCSS(custom, theme)}
      type="button"
      onClick={handleClick}
      variant={variant}
      aria-label={ariaLabel}
    >
      {children}
    </Self>
  );
};

type StyledIconButtonProps = MakeRequired<Pick<IconButtonProps, 'variant'>, 'variant'>;

const applyPrimaryStyle = (theme: Theme) => css`
  background-color: ${theme.colors.primary.base};

  & > svg {
    stroke: ${theme.colors.white};
  }

  &:hover {
    background-color: ${theme.colors.primary.light};
  }
  &:active {
    background-color: ${theme.colors.primary.dark};
  }
`;

const applySecondaryStyle = (theme: Theme) => css`
  background-color: ${theme.colors.white};

  & > svg {
    stroke: ${theme.colors.black};
  }

  &:hover,
  &:active {
    background-color: ${theme.colors.secondary.base};
  }
`;

export const Self = styled.button<StyledIconButtonProps>`
  ${({ theme, variant }) => css`
    display: flex;
    justify-content: center;
    align-items: center;

    font-size: ${theme.fontSize.md};
    border: none;
    border-radius: ${theme.radius.circle};
    transition: background-color 0.2s ease;

    ${applyPrimaryStyle(theme)}
    ${variant === 'secondary' && applySecondaryStyle(theme)}
  `}
`;

export default IconButton;
