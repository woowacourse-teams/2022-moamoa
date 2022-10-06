import { forwardRef } from 'react';

import * as S from '@components/select/Select.style';

export type SelectProps = {
  children: React.ReactNode;
  id?: string;
  defaultValue?: string | number;
  fluid?: boolean;
  disabled?: boolean;
};

const Select: React.FC<SelectProps> = forwardRef<HTMLSelectElement, SelectProps>(
  ({ children, id, defaultValue, fluid = false, disabled = false, ...props }, ref) => {
    return (
      <S.Select ref={ref} id={id} defaultValue={defaultValue} fluid={fluid} disabled={disabled} {...props}>
        {children}
      </S.Select>
    );
  },
);

Select.displayName = 'Select';

export default Select;
