import { forwardRef } from 'react';

import { Theme } from '@emotion/react';

import { noop } from '@utils';

import * as S from '@components/textarea/Textarea.style';

export type TextareaProps = {
  id: string;
  placeholder: string;
  disabled?: boolean;
  fontSize?: keyof Theme['fontSize'];
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
      <S.Textarea
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
