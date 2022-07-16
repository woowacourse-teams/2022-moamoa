import { memo } from 'react';

import * as S from './FilterButton.style';

export interface FilterButtonProps {
  shortTitle: string;
  description: string;
  isChecked: boolean;
  handleFilterButtonClick: React.ChangeEventHandler<HTMLInputElement>;
}

const FilterButton: React.FC<FilterButtonProps> = ({ shortTitle, description, isChecked, handleFilterButtonClick }) => {
  return (
    <S.FilterButtonContainer>
      <S.CheckBoxLabel isChecked={isChecked}>
        <S.ShortTitle>{shortTitle}</S.ShortTitle>
        <S.Description>{description}</S.Description>
        <S.CheckboxInput type="checkbox" onChange={handleFilterButtonClick} checked={isChecked} />
      </S.CheckBoxLabel>
    </S.FilterButtonContainer>
  );
};

export default memo(FilterButton);
