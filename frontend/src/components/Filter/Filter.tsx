import { useCallback, useState } from 'react';
import { BsPlusLg } from 'react-icons/bs';
import { FaFilter } from 'react-icons/fa';

import CheckListItem from '@components/CheckListItem/CheckListItem';
import DropBox from '@components/DropBox/DropBox';

import * as S from './Filter.style';

export interface TagItem {
  id: number;
  tagName: string;
}

export interface FilterProps {
  filters: Array<TagItem> | undefined;
  selectedFilters: Array<{ id: number }>;
  isOpen: boolean;
  searchInputValue: string;
  handlePlusButtonClick: React.MouseEventHandler<HTMLButtonElement>;
  handleFilterItemClick: (id: number) => React.MouseEventHandler<HTMLElement>;
  handleSearchInputChange: React.ChangeEventHandler<HTMLInputElement>;
}

const isSelected = (id: number, selectedFilters: Array<{ id: number }>) =>
  selectedFilters.some(({ id: selectedId }) => selectedId === id);

const Filter: React.FC<FilterProps> = ({
  filters,
  selectedFilters,
  isOpen,
  searchInputValue,
  handlePlusButtonClick,
  handleFilterItemClick,
  handleSearchInputChange,
}) => {
  return (
    <S.FilterContainer>
      <S.FilterLabel>
        <FaFilter /> <S.FilterText>필터</S.FilterText>
      </S.FilterLabel>
      <S.FilterButtonContainer>
        <S.FilterButton onClick={handlePlusButtonClick}>
          <BsPlusLg />
        </S.FilterButton>
        {isOpen && (
          <DropBox searchInputValue={searchInputValue} handleSearchInputChange={handleSearchInputChange}>
            {filters &&
              filters.map(({ id, tagName }) => (
                <S.FilterListItem key={id} onClick={handleFilterItemClick(id)}>
                  <CheckListItem isChecked={isSelected(id, selectedFilters)}>{tagName}</CheckListItem>
                </S.FilterListItem>
              ))}
          </DropBox>
        )}
      </S.FilterButtonContainer>
    </S.FilterContainer>
  );
};

export default Filter;
