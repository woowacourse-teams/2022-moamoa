import { forwardRef } from 'react';

import * as S from '@components/checkbox/Checkbox.style';

export type CheckboxProps = React.HTMLProps<HTMLInputElement> & { dataTagId: number };

const Checkbox = forwardRef<HTMLInputElement, CheckboxProps>(({ className, id, checked, onChange, dataTagId }, ref) => {
  return (
    <S.Checkbox
      ref={ref}
      type="checkbox"
      id={id}
      className={className}
      checked={checked}
      onChange={onChange}
      data-tagid={dataTagId}
    />
  );
});

Checkbox.displayName = 'Checkbox';

export default Checkbox;
