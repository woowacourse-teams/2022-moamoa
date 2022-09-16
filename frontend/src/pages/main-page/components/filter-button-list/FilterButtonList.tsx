import type { CategoryName, Tag, TagId, TagInfo } from '@custom-types';

import ButtonGroup from '@components/button-group/ButtonGroup';

import FilterButton from '@main-page/components/filter-button/FilterButton';

export type FilterButtonListProps = {
  filters: Array<Tag>;
  selectedFilters: Array<TagInfo>;
  onFilterButtonClick: (id: TagId, categoryName: CategoryName) => React.MouseEventHandler<HTMLButtonElement>;
};

const isSelected = (id: TagId, categoryName: CategoryName, selectedFilters: Array<TagInfo>) =>
  selectedFilters.some(filter => filter.id === id && filter.categoryName === categoryName);

const FilterButtonList: React.FC<FilterButtonListProps> = ({
  filters,
  selectedFilters,
  onFilterButtonClick: handleFilterButtonClick,
}) => {
  return (
    <ButtonGroup gap="12px">
      {filters.map(({ id, name, description, category }) => (
        <FilterButton
          key={id}
          name={name}
          description={description}
          isChecked={isSelected(id, category.name, selectedFilters)}
          onClick={handleFilterButtonClick(id, category.name)}
        />
      ))}
    </ButtonGroup>
  );
};

export default FilterButtonList;
