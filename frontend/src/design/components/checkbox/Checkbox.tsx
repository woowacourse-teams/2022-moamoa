import { forwardRef } from 'react';

import { noop } from '@utils';

import * as S from '@design/components/checkbox/Checkbox.style';

export type CheckboxProps = {
  children?: string;
  id?: string;
  checked?: boolean;
  dataTagId?: number;
  defaultChecked?: boolean;
  onChange?: React.ChangeEventHandler<HTMLInputElement>;
};

const Checkbox = forwardRef<HTMLInputElement, CheckboxProps>(
  ({ id, children, checked, onChange: handleChange = noop, dataTagId, defaultChecked, ...props }, ref) => {
    return (
      <label>
        <S.Checkbox
          id={id}
          ref={ref}
          type="checkbox"
          checked={checked}
          defaultChecked={defaultChecked}
          onChange={handleChange}
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
