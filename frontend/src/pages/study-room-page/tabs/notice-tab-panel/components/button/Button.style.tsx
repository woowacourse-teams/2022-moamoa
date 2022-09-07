import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

type ButtonProps = {
  variant?: 'primary' | 'danger';
};

const dynamicButton = {
  primary: (theme: Theme) => css`
    background-color: ${theme.colors.primary.base};
    color: ${theme.colors.white};
    border: 1px solid ${theme.colors.primary.base};
  `,
  danger: (theme: Theme) => css`
    background-color: ${theme.colors.red};
    color: ${theme.colors.white};
    border: 1px solid ${theme.colors.red};
  `,
};

export const Button = styled.button<ButtonProps>`
  ${({ theme, variant = 'primary' }) => css`
    display: inline-block;
    padding: 0 10px;
    height: 28px;
    line-height: 28px;

    transition: opacity 0.2 ease;
    &:hover {
      opacity: 0.8;
    }

    font-size: ${theme.fontSize.sm};
    border-radius: 3px;
    text-decoration: none !important;
    vertical-align: middle;
    text-shadow: none;
    box-shadow: none;
    transition-duration: 0.3s;
    box-sizing: content-box;

    ${dynamicButton[variant](theme)}
  `}
`;
