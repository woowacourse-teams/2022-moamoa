import { memo } from 'react';

import * as S from './FilterButton.style';

export interface FilterButtonProps {
  shortName: string;
  fullName: string;
  isChecked: boolean;
  handleFilterButtonClick: React.MouseEventHandler<HTMLButtonElement>;
}

const FilterButton: React.FC<FilterButtonProps> = ({ shortName, fullName, isChecked, handleFilterButtonClick }) => {
  return (
    <S.FilterButtonContainer>
      <S.CheckBoxButton isChecked={isChecked} onClick={handleFilterButtonClick}>
        <S.ShortName>{shortName}</S.ShortName>
        <S.FullName>{fullName}</S.FullName>
      </S.CheckBoxButton>
    </S.FilterButtonContainer>
  );
};

export default memo(FilterButton);
