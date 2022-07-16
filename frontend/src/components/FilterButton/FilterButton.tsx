import * as S from './FilterButton.style';

export interface FilterButtonProps {
  shortTitle: string;
  description: string;
  isChecked: boolean;
  handleFilterButtonClick: React.MouseEventHandler<HTMLInputElement>;
}

const FilterButton: React.FC<FilterButtonProps> = ({ shortTitle, description, isChecked, handleFilterButtonClick }) => {
  return (
    <S.CheckBoxLabel isChecked={isChecked}>
      <S.ShortTitle>{shortTitle}</S.ShortTitle>
      <S.Description>{description}</S.Description>
      <S.CheckboxInput type="checkbox" onClick={handleFilterButtonClick} checked={isChecked} />
    </S.CheckBoxLabel>
  );
};

export default FilterButton;
