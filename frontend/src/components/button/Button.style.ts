import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { ButtonProp } from '@components/button/Button';

export const Button = styled.button<ButtonProp>`
  ${({ theme, fluid }) => css`
    width: ${fluid ? '100%' : 'auto'};
    padding: 20px 10px;

    text-align: center;
    font-size: 24px;
    border: none;
    border-radius: 10px;
    background: #1a237e;
    color: white;

    white-space: nowrap;

    &:disabled {
      background-color: ${theme.colors.secondary.base};
      color: ${theme.colors.black};
    }
  `}
`;
