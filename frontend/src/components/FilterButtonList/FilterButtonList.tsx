import type { Filter } from '@custom-types/index';

import type { FilterInfo } from '@pages/MainPage/FilterSection/FilterSection';

import FilterButton from '@components/FilterButton/FilterButton';

import * as S from './FilterButtonList.style';

export interface FilterButtonListProps {
  filters: Array<Filter>;
  selectedFilters: Array<FilterInfo>;
  handleFilterButtonClick: (id: number, categoryName: string) => React.MouseEventHandler<HTMLButtonElement>;
}

const isSelected = (id: number, categoryName: string, selectedFilters: Array<{ id: number; categoryName: string }>) =>
  selectedFilters.some(filter => filter.id === id && filter.categoryName === categoryName);

const FilterButtonList: React.FC<FilterButtonListProps> = ({ filters, selectedFilters, handleFilterButtonClick }) => {
  return (
    <S.FilterButtons>
      {filters.map(({ id, shortName, fullName, category: { name } }) => (
        <li key={id}>
          <FilterButton
            shortName={shortName}
            fullName={fullName}
            isChecked={isSelected(id, name, selectedFilters)}
            handleFilterButtonClick={handleFilterButtonClick(id, name)}
          />
        </li>
      ))}
    </S.FilterButtons>
  );
};

export default FilterButtonList;
