import { SerializedStyles, css } from '@emotion/react';
import styled from '@emotion/styled';

import type { ButtonProp } from '@components/button/Button';

type OutlineButtonStyleFn = () => SerializedStyles;

const outlineButtonStyleFn: OutlineButtonStyleFn = () => css`
  transition: 0.3s;
  background-color: transparent;
  border: 1px solid #1a237e;
  color: #1b247e;

  &:hover {
    background-color: #1a237e;
    border: 1px solid transparent;
    color: white;
  }
`;

export const Button = styled.button<ButtonProp>`
  ${({ fluid, outline }) => css`
    width: ${fluid ? '100%' : 'auto'};
    padding: 20px 10px;

    text-align: center;
    font-size: 24px;
    border: none;
    border-radius: 10px;
    background-color: #1a237e;
    color: white;

    white-space: nowrap;

    ${outline && outlineButtonStyleFn()}
  `}
`;
