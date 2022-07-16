import { useQuery } from 'react-query';

import type { Filter, FilterListQueryData } from '@custom-types/index';

import { getFilterList } from '@api/getFilterList';

import FilterButtonList from '@components/FilterButtonList/FilterButtonList';

import * as S from './FilterSection.style';

export interface FilterInfo {
  id: number;
  categoryName: string;
}

export interface FilterSectionProps {
  selectedFilters: Array<FilterInfo>;
  handleFilterButtonClick: (id: number, categoryName: string) => React.ChangeEventHandler<HTMLInputElement>;
}

const filterByCategory = (filters: Array<Filter> | undefined, categoryId: number) =>
  filters?.filter(filter => filter.category.id === categoryId) || [];

const FilterSection: React.FC<FilterSectionProps> = ({ selectedFilters, handleFilterButtonClick }) => {
  const { data, isLoading, isError, error } = useQuery<FilterListQueryData, Error>('filters', getFilterList);

  const generationFilters = filterByCategory(data?.filters, 1);
  const areaFilters = filterByCategory(data?.filters, 2);
  const tagFilters = filterByCategory(data?.filters, 3);

  return (
    <S.FilterSectionContainer>
      {isLoading && <div>로딩 중...</div>}
      {isError && <div>{error.message}</div>}
      <S.FilterSectionHeader>필터</S.FilterSectionHeader>
      <FilterButtonList
        filters={areaFilters}
        selectedFilters={selectedFilters}
        handleFilterButtonClick={handleFilterButtonClick}
      />
      <S.VerticalLine />
      <FilterButtonList
        filters={generationFilters}
        selectedFilters={selectedFilters}
        handleFilterButtonClick={handleFilterButtonClick}
      />
      <S.VerticalLine />
      <FilterButtonList
        filters={tagFilters}
        selectedFilters={selectedFilters}
        handleFilterButtonClick={handleFilterButtonClick}
      />
    </S.FilterSectionContainer>
  );
};

export default FilterSection;
