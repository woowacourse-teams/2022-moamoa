import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

import type { ButtonProps } from '@components/button/Button';

const applyOutlineButtonStyle = ({ theme }: { theme: Theme }) => css`
  transition: 0.3s;
  background-color: transparent;
  border: 1px solid ${theme.colors.primary.base};
  color: ${theme.colors.primary.base};

  &:hover {
    background-color: ${theme.colors.primary.base};
    border: 1px solid transparent;
    color: ${theme.colors.white};
  }
`;

export const Button = styled.button<ButtonProps>`
  ${({ theme, fluid, outline }) => css`
    width: ${fluid ? '100%' : 'auto'};
    padding: 20px 10px;
    min-height: 30px;

    text-align: center;
    font-size: ${theme.fontSize.lg};
    color: ${theme.colors.white};

    border: none;
    border-radius: 10px;
    background-color: ${theme.colors.primary.base};

    white-space: nowrap;

    ${outline && applyOutlineButtonStyle({ theme })}

    &:hover {
      background-color: ${theme.colors.primary.light};
    }

    &:active {
      background-color: ${theme.colors.primary.dark};
    }

    &:disabled {
      background-color: ${theme.colors.secondary.base};
      color: ${theme.colors.black};
    }
  `}
`;
