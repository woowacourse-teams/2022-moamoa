import { forwardRef } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { noop } from '@utils';

import { type ThemeFontSize } from '@styles/theme';

export type TextareaProps = {
  id: string;
  placeholder: string;
  disabled?: boolean;
  fontSize?: ThemeFontSize;
  fluid?: boolean;
  invalid: boolean;
  border?: boolean;
  defaultValue?: string | number;
  value?: string | number;
  onChange?: React.ChangeEventHandler<HTMLTextAreaElement>;
};

const Textarea: React.FC<TextareaProps> = forwardRef<HTMLTextAreaElement, TextareaProps>(
  (
    {
      id,
      placeholder,
      disabled = false,
      fontSize = 'md',
      fluid = true,
      invalid = false,
      border = true,
      defaultValue,
      onChange: handleChange = noop,
      ...props
    },
    ref,
  ) => {
    return (
      <Self
        id={id}
        ref={ref}
        placeholder={placeholder}
        onChange={handleChange}
        disabled={disabled}
        fontSize={fontSize}
        fluid={fluid}
        invalid={invalid}
        border={border}
        defaultValue={defaultValue}
        {...props}
      />
    );
  },
);

Textarea.displayName = 'Textarea';

export default Textarea;

type StyledTextareaProps = Required<Pick<TextareaProps, 'fontSize' | 'invalid' | 'fluid' | 'border'>>;

const Self = styled.textarea<StyledTextareaProps>`
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
