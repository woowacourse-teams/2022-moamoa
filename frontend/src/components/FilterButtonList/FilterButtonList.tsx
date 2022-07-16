import type { Filter } from '@custom-types/index';

import FilterButton from '@components/FilterButton/FilterButton';
import type { FilterInfo } from '@components/FilterSection/FilterSection';

import * as S from './FilterButtonList.style';

export interface FilterButtonListProps {
  filters: Array<Filter>;
  selectedFilters: Array<FilterInfo>;
  handleFilterButtonClick: (id: number, categoryName: string) => React.ChangeEventHandler<HTMLInputElement>;
}

const isSelected = (id: number, categoryName: string, selectedFilters: Array<{ id: number; categoryName: string }>) =>
  selectedFilters.some(filter => filter.id === id && filter.categoryName === categoryName);

const FilterButtonList: React.FC<FilterButtonListProps> = ({ filters, selectedFilters, handleFilterButtonClick }) => {
  return (
    <S.FilterButtons>
      {filters.map(({ id, shortName, description, category: { name } }) => (
        <li key={id}>
          <FilterButton
            shortTitle={shortName}
            description={description}
            isChecked={isSelected(id, name, selectedFilters)}
            handleFilterButtonClick={handleFilterButtonClick(id, name)}
          />
        </li>
      ))}
    </S.FilterButtons>
  );
};

export default FilterButtonList;
