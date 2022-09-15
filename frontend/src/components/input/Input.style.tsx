import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { InputProps } from '@components/input/Input';

type StyledInputProps = Required<Pick<InputProps, 'fontSize' | 'invalid' | 'fluid'>>;

export const Input = styled.input<StyledInputProps>`
  ${({ theme, fontSize, invalid, fluid }) => css`
    width: ${fluid ? '100%' : 'auto'};
    padding: 4px 8px;

    font-size: ${theme.fontSize[fontSize]};
    border-radius: ${theme.radius.sm};
    border: 1px solid ${theme.colors.secondary.base};
    background-color: ${theme.colors.white};
    outline: none;

    &:focus {
      border: 1px solid ${theme.colors.primary.light};
    }

    ${invalid &&
    css`
      border: 1px solid ${theme.colors.red} !important;
    `}

    &:disabled {
      color: ${theme.colors.secondary.base};
      border: 1px solid ${theme.colors.secondary.base};
      background-color: ${theme.colors.secondary.light};
    }
  `}
`;
