import type { Tag, TagInfo } from '@custom-types/index';

import * as S from '@main-page/components/filter-section/filter-button-list/FilterButtonList.style';
import FilterButton from '@main-page/components/filter-section/filter-button/FilterButton';

export type FilterButtonListProps = {
  filters: Array<Tag>;
  selectedFilters: Array<TagInfo>;
  onFilterButtonClick: (id: number, categoryName: string) => React.MouseEventHandler<HTMLButtonElement>;
};

const isSelected = (id: number, categoryName: string, selectedFilters: Array<{ id: number; categoryName: string }>) =>
  selectedFilters.some(filter => filter.id === id && filter.categoryName === categoryName);

const FilterButtonList: React.FC<FilterButtonListProps> = ({
  filters,
  selectedFilters,
  onFilterButtonClick: handleFilterButtonClick,
}) => {
  return (
    <S.FilterButtons>
      {filters.map(({ id, name, description, category }) => (
        <li key={id}>
          <FilterButton
            name={name}
            description={description}
            isChecked={isSelected(id, category.name, selectedFilters)}
            onFilterButtonClick={handleFilterButtonClick(id, category.name)}
          />
        </li>
      ))}
    </S.FilterButtons>
  );
};

export default FilterButtonList;
