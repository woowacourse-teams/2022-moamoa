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
  filters: Array<TagItem>;
  selectedFilters: Array<{ id: number }>;
  handleFilterItemClick: (id: number) => React.MouseEventHandler<HTMLElement>;
}

const Filter: React.FC<FilterProps> = ({ filters, selectedFilters, handleFilterItemClick }) => {
  const [isOpen, setIsOpen] = useState(false);

  const handlePlusButtonClick = () => {
    setIsOpen(prev => !prev);
  };

  const isSelected = useCallback(
    (id: number) => selectedFilters.some(({ id: selectedId }) => selectedId === id),
    [selectedFilters],
  );

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
          <DropBox>
            {filters.map(({ id, tagName }) => (
              <S.FilterListItem key={id} onClick={handleFilterItemClick(id)}>
                <CheckListItem isChecked={isSelected(id)}>{tagName}</CheckListItem>
              </S.FilterListItem>
            ))}
          </DropBox>
        )}
      </S.FilterButtonContainer>
    </S.FilterContainer>
  );
};

export default Filter;
