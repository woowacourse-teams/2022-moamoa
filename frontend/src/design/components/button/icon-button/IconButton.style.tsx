import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

import { IconButtonProps } from './IconButton';

type StyleIconButtonProps = Pick<IconButtonProps, 'variant' | 'height' | 'width'>;

const applyPrimaryStyle = (theme: Theme) => css`
  background-color: ${theme.colors.primary.base};

  & > svg {
    stroke: ${theme.colors.white};
    fill: ${theme.colors.white};
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
    fill: ${theme.colors.black};
  }

  &:hover,
  &:active {
    background-color: ${theme.colors.secondary.base};
  }
`;

export const IconButton = styled.button<StyleIconButtonProps>`
  ${({ theme, variant, height, width }) => css`
    display: flex;
    justify-content: center;
    align-items: center;

    width: ${width};
    height: ${height};

    border: none;
    border-radius: ${theme.radius.circle};
    transition: background-color 0.2s ease;

    ${variant === 'secondary' ? applySecondaryStyle(theme) : applyPrimaryStyle(theme)}
  `}
`;
