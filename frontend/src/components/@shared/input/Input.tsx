import { forwardRef } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { noop } from '@utils';

import { type ThemeFontSize } from '@styles/theme';

export type InputProps = {
  id: string;
  type: 'text' | 'number' | 'date' | 'url';
  placeholder?: string;
  disabled?: boolean;
  fontSize?: ThemeFontSize;
  fluid?: boolean;
  invalid?: boolean;
  defaultValue?: string | number;
  onChange?: React.ChangeEventHandler<HTMLInputElement>;
  onKeyDown?: React.KeyboardEventHandler<HTMLInputElement>;
};

const Input: React.FC<InputProps> = forwardRef<HTMLInputElement, InputProps>(
  (
    {
      id,
      type,
      placeholder,
      disabled = false,
      fontSize = 'md',
      invalid = false,
      fluid = false,
      defaultValue,
      onChange: handleChange = noop,
      onKeyDown: handleKeyDown = noop,
      ...props
    },
    ref,
  ) => {
    return (
      <Self
        id={id}
        ref={ref}
        type={type}
        placeholder={placeholder}
        onChange={handleChange}
        onKeyDown={handleKeyDown}
        disabled={disabled}
        fontSize={fontSize}
        invalid={invalid}
        fluid={fluid}
        defaultValue={defaultValue}
        {...props}
      />
    );
  },
);

Input.displayName = 'Input';

export default Input;

type StyledInputProps = Required<Pick<InputProps, 'fontSize' | 'invalid' | 'fluid'>>;

const Self = styled.input<StyledInputProps>`
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
