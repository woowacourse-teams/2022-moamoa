import { forwardRef } from 'react';

import * as S from '@components/positive-number-input/PositiveNumberInput.style';

type NumberInputProps = {
  id?: string;
  placeholder?: string;
  className?: string;
  value: number;
  max?: number;
  onChange: (val: string) => void;
};

const PositiveNumberInput = forwardRef<HTMLInputElement, NumberInputProps>(({ id, value, onChange }, ref) => {
  const handleChange = ({ target }: React.ChangeEvent<HTMLInputElement>) => {
    const { selectionStart } = target;
    if (selectionStart === null) return;

    let cursor = Math.max(0, selectionStart);
    const newValueStr = target.value.replace(/[^0-9]/g, '');
    if (newValueStr === '') {
      onChange('');
      return;
    }

    // 숫자만 입력 했는지 검사
    if (newValueStr.length < target.value.length) {
      cursor = Math.max(0, selectionStart - 1);
    }

    queueMicrotask(() => {
      target.setSelectionRange(cursor, cursor);
    });

    onChange(newValueStr);
  };

  return <S.NumberInput id={id} type="text" onChange={handleChange} value={value} ref={ref} />;
});

PositiveNumberInput.displayName = 'PositiveNumberInput';

export default PositiveNumberInput;
