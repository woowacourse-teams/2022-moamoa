import { forwardRef } from 'react';

import { Theme } from '@emotion/react';

import { noop } from '@utils';

import * as S from '@design/components/input/Input.style';

export type InputProps = {
  id: string;
  type: 'text' | 'number' | 'date';
  placeholder?: string;
  disabled?: boolean;
  fontSize?: keyof Theme['fontSize'];
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
      <S.Input
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
