import { forwardRef } from 'react';

import * as S from '@components/positive-number-input/PositiveNumberInput.style';

type NumberInputProps = {
  id?: string;
  placeholder?: string;
  className?: string;
  value: number;
  max?: number;
  onChange: (val: number) => void;
};

const PositiveNumberInput = forwardRef<HTMLInputElement, NumberInputProps>(
  ({ className, value, max, onChange }, ref) => {
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      const { target } = e;
      const { selectionStart } = target;
      if (selectionStart === null) return;

      let cursor = Math.max(0, selectionStart);
      const newValueStr = target.value.replace(/[^0-9]/g, '');
      const newValueNum = Number(newValueStr);

      // 숫자만 입력 했는지 검사
      if (newValueStr.length < target.value.length) {
        cursor = Math.max(0, selectionStart - 1);
      }

      queueMicrotask(() => {
        target.setSelectionRange(cursor, cursor);
      });

      onChange(newValueNum);
    };

    return (
      <S.NumberInput
        className={className}
        type="text"
        onChange={handleChange}
        value={value === 0 ? '' : value}
        ref={ref}
      />
    );
  },
);

PositiveNumberInput.displayName = 'PositiveNumberInput';

export default PositiveNumberInput;
