import { type Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@utils';

import { type ToggleButtonProps } from '@components/button/toggle-button/ToggleButton';

type StyleToggleButtonProps = Required<Pick<ToggleButtonProps, 'checked' | 'fluid' | 'variant' | 'fontSize'>>;

const applyCheckedStyle = (theme: Theme) => css`
  color: ${theme.colors.primary.base};
  border-bottom: 2px solid ${theme.colors.primary.base};

  &:hover,
  &:active {
    color: ${theme.colors.primary.base};
    border-bottom: 2px solid ${theme.colors.primary.base};
  }
`;

export const ToggleButton = styled.button<StyleToggleButtonProps>`
  ${({ theme, checked, fluid, variant, fontSize }) => css`
    display: flex;
    justify-content: center;
    align-items: center;
    column-gap: 4px;

    width: ${fluid ? '100%' : 'auto'};
    padding: 8px 4px;

    color: ${variant === 'secondary' ? theme.colors.secondary.dark : theme.colors.primary.base};
    font-size: ${theme.fontSize[fontSize]};
    border: none;
    border-bottom: 2px solid transparent;
    background-color: transparent;
    transition: border-bottom 0.2s ease;

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