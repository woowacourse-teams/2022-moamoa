import { forwardRef } from 'react';

import * as S from '@components/positive-number-input/PositiveNumberInput.style';

type NumberInputProps = {
  id?: string;
  placeholder?: string;
  className?: string;
  value: number;
  max?: number;
  onChange: (val: number | '') => void;
};

const nums = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];
const arrows = ['ArrowLeft', 'ArrowRight', 'ArrowDown', 'ArrowUp'];

const PositiveNumberInput = forwardRef<HTMLInputElement, NumberInputProps>(({ id, value, onChange, ...props }, ref) => {
  const handleChange = ({ target }: React.ChangeEvent<HTMLInputElement>) => {
    if (target.value === '') {
      onChange('');
      return;
    }
    onChange(Number(target.value));
  };

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    const { key } = e;
    if (key !== 'Backspace' && !nums.includes(key) && !arrows.includes(key)) {
      e.preventDefault();
      return;
    }
  };

  return (
    <S.NumberInput
      {...props}
      id={id}
      type="number"
      onChange={handleChange}
      onKeyDown={handleKeyDown}
      value={value.toString()}
      ref={ref}
    />
  );
});

PositiveNumberInput.displayName = 'PositiveNumberInput';

export default PositiveNumberInput;
