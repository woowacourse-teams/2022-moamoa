import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

import type { MakeRequired } from '@custom-types';

import { type IconButtonProps } from '@components/button/icon-button/IconButton';

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

export const IconButton = styled.button<StyledIconButtonProps>`
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
