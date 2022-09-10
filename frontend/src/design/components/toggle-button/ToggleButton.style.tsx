import { type Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@utils';

import { type ToggleButtonProps } from '@design/components/toggle-button/ToggleButton';

type StyleToggleButtonProps = Pick<ToggleButtonProps, 'checked' | 'fluid'>;

const applyCheckedStyle = (theme: Theme) => css`
  border-bottom: 2px solid ${theme.colors.primary.base};

  &:hover,
  &:active {
    border-bottom: 2px solid ${theme.colors.primary.base};
  }
`;

export const ToggleButton = styled.button<StyleToggleButtonProps>`
  ${({ theme, checked, fluid }) => css`
    display: flex;
    justify-content: center;
    align-items: center;
    column-gap: 4px;

    width: ${fluid ? '100%' : 'auto'};
    padding: 8px 4px;

    color: ${theme.colors.primary.base};
    border: none;
    border-bottom: 2px solid transparent;
    background-color: transparent;

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

    & > * {
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
