import { CgClose } from 'react-icons/cg';

import * as S from './FilterChip.style';

export interface FilterChipProps {
  children: string;
  handleCloseButtonClick: React.MouseEventHandler<HTMLButtonElement>;
}

const FilterChip: React.FC<FilterChipProps> = ({ children, handleCloseButtonClick }) => {
  return (
    <S.StyledFilterChip>
      <S.FilterSpan>{children}</S.FilterSpan>
      <S.FilterCloseButton type="button" onClick={handleCloseButtonClick}>
        <CgClose />
      </S.FilterCloseButton>
    </S.StyledFilterChip>
  );
};

export default FilterChip;
