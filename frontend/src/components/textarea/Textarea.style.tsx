import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { TextareaProps } from '@components/textarea/Textarea';

type StyledTextareaProps = Required<Pick<TextareaProps, 'fontSize' | 'invalid' | 'fluid' | 'border'>>;

export const Textarea = styled.textarea<StyledTextareaProps>`
  ${({ theme, fontSize, fluid, invalid, border }) => css`
    width: ${fluid ? '100%' : 'auto'};
    height: 100%;
    padding: 8px;

    font-size: ${theme.fontSize[fontSize]};
    border-radius: ${theme.radius.sm};
    border: ${border ? `1px solid ${theme.colors.secondary.base}` : 'none'};
    background-color: ${theme.colors.secondary.light};
    outline: none;
    overflow: auto;

    &:focus {
      border: 1px solid ${theme.colors.primary.light};
      background-color: ${theme.colors.white};
    }

    ${invalid &&
    css`
      border: 1px solid ${theme.colors.red};

      &:focus {
        border: 1px solid ${theme.colors.red};
      }
    `}

    &:disabled {
      color: ${theme.colors.secondary.base};
      border: 1px solid ${theme.colors.secondary.base};
      background-color: ${theme.colors.secondary.light};
    }
  `}
`;