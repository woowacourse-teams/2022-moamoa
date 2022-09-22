import { type ReactSVG } from 'react';

import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

import type { CssLength, MakeRequired } from '@custom-types';

import { type ThemeFontSize } from '@styles/theme';

export type IconButtonProps = {
  children: React.ReactElement<ReactSVG>;
  ariaLabel: string;
  variant?: 'primary' | 'secondary';
  width: CssLength;
  height: CssLength;
  fontSize?: ThemeFontSize;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const IconButton: React.FC<IconButtonProps> = ({
  children,
  ariaLabel,
  variant = 'primary',
  width,
  height,
  fontSize = 'md',
  onClick: handleClick,
}) => {
  return (
    <Self
      type="button"
      onClick={handleClick}
      variant={variant}
      aria-label={ariaLabel}
      width={width}
      height={height}
      fontSize={fontSize}
    >
      {children}
    </Self>
  );
};

type StyledIconButtonProps = MakeRequired<
  Pick<IconButtonProps, 'variant' | 'height' | 'width' | 'fontSize'>,
  'variant'
>;

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
  ${({ theme, variant, height, width, fontSize }) => css`
    display: flex;
    justify-content: center;
    align-items: center;

    width: ${width};
    height: ${height};

    ${fontSize && `font-size: ${theme.fontSize[fontSize]};`}
    border: none;
    border-radius: ${theme.radius.circle};
    transition: background-color 0.2s ease;

    ${applyPrimaryStyle(theme)}
    ${variant === 'secondary' && applySecondaryStyle(theme)}
  `}
`;

export default IconButton;
