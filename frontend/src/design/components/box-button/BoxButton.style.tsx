import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

import { type BoxButtonProps } from '@design/components/box-button/BoxButton';

type StyleBoxButtonProps = Pick<BoxButtonProps, 'disabled' | 'fluid' | 'variant'>;

const applyFilledButtonStyle = (theme: Theme) => css`
  border: none;
  background-color: ${theme.colors.primary.base};

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
  background-color: transparent;
  border: 1px solid ${theme.colors.primary.base};
  color: ${theme.colors.primary.base};

  transition: border-color 0.3s ease, color 0.3s ease;

  &:hover {
    border-color: ${theme.colors.primary.light};
    color: ${theme.colors.primary.light};
  }

  &:active {
    border-color: ${theme.colors.primary.dark};
    color: ${theme.colors.primary.dark};
  }
`;

export const BoxButton = styled.button<StyleBoxButtonProps>`
  ${({ theme, fluid, disabled, variant }) => css`
    width: ${fluid ? '100%' : 'auto'};
    padding: 20px 10px;
    min-height: 30px;

    border-radius: ${theme.radius.sm};
    text-align: center;
    font-size: ${theme.fontSize.lg};
    color: ${theme.colors.white};

    ${variant === 'secondary' ? applyOutlineButtonStyle(theme) : applyFilledButtonStyle(theme)}

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
