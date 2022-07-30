import { memo } from 'react';

import * as S from '@pages/main-page/components/filter-section/filter-button/FilterButton.style';

export interface FilterButtonProps {
  name: string;
  description: string;
  isChecked: boolean;
  handleFilterButtonClick: React.MouseEventHandler<HTMLButtonElement>;
}

const FilterButton: React.FC<FilterButtonProps> = ({ name, description, isChecked, handleFilterButtonClick }) => {
  return (
    <S.FilterButtonContainer>
      <S.CheckBoxButton isChecked={isChecked} onClick={handleFilterButtonClick}>
        <S.Name>{name}</S.Name>
        <S.Description>{description}</S.Description>
      </S.CheckBoxButton>
    </S.FilterButtonContainer>
  );
};

export default memo(FilterButton);
