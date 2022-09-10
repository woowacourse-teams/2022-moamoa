import { forwardRef } from 'react';

import * as S from '@design/components/checkbox/Checkbox.style';

export type CheckboxProps = {
  children: string;
  checked: boolean;
  dataTagId?: number;
  onChange: React.ChangeEventHandler<HTMLInputElement>;
};

const Checkbox = forwardRef<HTMLInputElement, CheckboxProps>(
  ({ children, checked, onChange, dataTagId, ...props }, ref) => {
    return (
      <label>
        <S.Checkbox
          ref={ref}
          type="checkbox"
          checked={checked}
          onChange={onChange}
          data-tagid={dataTagId} // TODO: 도메인 빼자
          {...props}
        />
        {children}
      </label>
    );
  },
);

Checkbox.displayName = 'Checkbox';

export default Checkbox;
