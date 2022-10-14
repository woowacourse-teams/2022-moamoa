import { forwardRef } from 'react';

import styled from '@emotion/styled';

import { noop } from '@utils';

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
        <Self
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

const Self = styled.input`
  margin-right: 4px;

  &:checked {
    border: none;
    outline: none;
  }
`;

export default Checkbox;
