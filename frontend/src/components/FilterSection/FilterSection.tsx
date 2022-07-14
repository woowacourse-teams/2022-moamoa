import React, { useState } from 'react';
import { useQuery } from 'react-query';

import { getTagList, getTagListSearchedByTagName } from '@api/getTagList';

import Filter from '@components/Filter/Filter';
import type { TagItem } from '@components/Filter/Filter';
import FilterChip from '@components/FilterChip/FilterChip';

// TODO: hooks 폴더 절대 경로 설정
import { useThrottledValue } from '../../hooks/useThrottledValue';
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
  const [searchedTag, setSearchedTag] = useState('');

  const [throttledTag] = useThrottledValue(searchedTag);

  const { data } = useQuery<unknown, unknown, TagListQueryData>(['filters'], getTagList, {
    enabled: isOpen,
  });
  const { data: searchedData } = useQuery<unknown, unknown, TagListQueryData>(
    ['searched-filters', throttledTag],
    () => getTagListSearchedByTagName(searchedTag),
    {
      enabled: !!searchedTag,
    },
  );

  const filters = searchedTag.length ? searchedData?.tags : data?.tags;

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

  const handleSearchedTagChange = ({ target }: React.ChangeEvent<HTMLInputElement>) => {
    setSearchedTag(target.value);
  };

  return (
    <S.FilterSectionContainer>
      <Filter
        filters={filters}
        selectedFilters={selectedFilters}
        isOpen={isOpen}
        searchInputValue={searchedTag}
        handleFilterItemClick={handleFilterItemClick}
        handlePlusButtonClick={handlePlusButtonClick}
        handleSearchInputChange={handleSearchedTagChange}
      />
      <S.FilterChipList>
        {selectedFilters.map(({ id }) => (
          <li key={id}>
            <FilterChip handleCloseButtonClick={handleChipCloseButtonClick(id)}>
              {findTagNameById(id, data?.tags)}
            </FilterChip>
          </li>
        ))}
      </S.FilterChipList>
    </S.FilterSectionContainer>
  );
};

export default FilterSection;
