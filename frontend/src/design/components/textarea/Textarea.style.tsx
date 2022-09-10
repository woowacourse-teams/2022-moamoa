import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { TextareaProps } from '@design/components/textarea/Textarea';

type StyleTextareaProps = Required<Pick<TextareaProps, 'fontSize' | 'invalid' | 'fluid'>>;

export const Textarea = styled.textarea<StyleTextareaProps>`
  ${({ theme, fontSize, fluid, invalid }) => css`
    width: ${fluid ? '100%' : 'auto'};
    height: 100%;
    padding: 8px;

    font-size: ${theme.fontSize[fontSize]};
    border-radius: ${theme.radius.sm};
    border: 1px solid ${theme.colors.secondary.base};
    background-color: ${theme.colors.secondary.light};
    outline: none;
    overflow: auto;

    &:focus {
      border: 1px solid ${theme.colors.primary.light};
      background-color: ${theme.colors.white};
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
