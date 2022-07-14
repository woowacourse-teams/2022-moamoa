import { useState } from 'react';
import { useQuery } from 'react-query';

import { getTagList } from '@api/getTagList';

import Filter from '@components/Filter/Filter';
import type { TagItem } from '@components/Filter/Filter';
import FilterChip from '@components/FilterChip/FilterChip';

import * as S from './FilterSection.style';

export type TagListQueryData = {
  tags: Array<TagItem>;
};

const findTagNameById = (selectedId: number, tags: Array<TagItem> | undefined) =>
  tags?.find(({ id }) => selectedId === id)?.tagName || '%ERROR%';

const FilterSection: React.FC = () => {
  // TODO: selectedFilters context API로 관리
  const [selectedFilters, setSelectedFilters] = useState<Array<{ id: number }>>([]);
  const [isOpen, setIsOpen] = useState(false);

  const { data } = useQuery<unknown, unknown, TagListQueryData>(['filters', selectedFilters], getTagList, {
    enabled: isOpen,
  });

  const handlePlusButtonClick = () => {
    setIsOpen(prev => !prev);
  };

  const handleFilterItemClick = (selectedId: number) => () => {
    setSelectedFilters(prev => {
      if (prev.some(({ id }) => selectedId === id)) {
        return prev.filter(({ id }) => selectedId !== id);
      }
      return [...prev, { id: selectedId }];
    });
  };

  const handleChipCloseButtonClick = (selectedId: number) => () => {
    setSelectedFilters(prev => prev.filter(({ id }) => selectedId !== id));
  };

  return (
    <S.FilterSectionContainer>
      <Filter
        filters={data?.tags}
        selectedFilters={selectedFilters}
        handleFilterItemClick={handleFilterItemClick}
        handlePlusButtonClick={handlePlusButtonClick}
        isOpen={isOpen}
      />
      <S.FilterChipList>
        {selectedFilters.map(({ id }) => (
          <li>
            <FilterChip key={id} handleCloseButtonClick={handleChipCloseButtonClick(id)}>
              {findTagNameById(id, data?.tags)}
            </FilterChip>
          </li>
        ))}
      </S.FilterChipList>
    </S.FilterSectionContainer>
  );
};

export default FilterSection;
