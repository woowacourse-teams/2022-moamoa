import { useCallback, useState } from 'react';

import { filters } from '@components/DropBox/DropBox.stories';
import Filter from '@components/Filter/Filter';
import FilterChip from '@components/FilterChip/FilterChip';

import * as S from './FilterSection.style';

const FilterSection: React.FC = () => {
  // TODO: selectedFilters context API로 관리
  const [selectedFilters, setSelectedFilters] = useState<Array<{ id: number }>>([]);

  const handleFilterItemClick = (selectedId: number) => () => {
    setSelectedFilters(prev => {
      if (prev.some(({ id }) => selectedId === id)) {
        return prev.filter(({ id }) => selectedId !== id);
      }
      return [...prev, { id: selectedId }];
    });
  };

  const handleCloseButtonClick = (selectedId: number) => () => {
    setSelectedFilters(prev => prev.filter(({ id }) => selectedId !== id));
  };

  const findTagNameById = useCallback(
    (selectedId: number) => filters.find(({ id }) => selectedId === id)?.tagName || '%ERROR%',
    [filters],
  );

  return (
    <S.FilterSectionContainer>
      <Filter filters={filters} selectedFilters={selectedFilters} handleFilterItemClick={handleFilterItemClick} />
      <S.FilterChipList>
        {selectedFilters.map(({ id }) => (
          <li>
            <FilterChip key={id} handleCloseButtonClick={handleCloseButtonClick(id)}>
              {findTagNameById(id)}
            </FilterChip>
          </li>
        ))}
      </S.FilterChipList>
    </S.FilterSectionContainer>
  );
};

export default FilterSection;
