import { memo } from 'react';

import * as S from './FilterButton.style';

export interface FilterButtonProps {
  shortName: string;
  fullName: string;
  isChecked: boolean;
  handleFilterButtonClick: React.ChangeEventHandler<HTMLInputElement>;
}

const FilterButton: React.FC<FilterButtonProps> = ({ shortName, fullName, isChecked, handleFilterButtonClick }) => {
  return (
    <S.FilterButtonContainer>
      <S.CheckBoxLabel isChecked={isChecked}>
        <S.ShortName>{shortName}</S.ShortName>
        <S.FullName>{fullName}</S.FullName>
        <S.CheckboxInput type="checkbox" onChange={handleFilterButtonClick} checked={isChecked} />
      </S.CheckBoxLabel>
    </S.FilterButtonContainer>
  );
};

export default memo(FilterButton);
