import { useRef } from 'react';
import { useQuery } from 'react-query';

import type { Filter, FilterListQueryData } from '@custom-types/index';

import { getFilterList } from '@api/getFilterList';

import FilterButtonList from '@components/FilterButtonList/FilterButtonList';
import SlideButton from '@components/SlideButton/SlideButton';

import * as S from './FilterSection.style';

export interface FilterInfo {
  id: number;
  categoryName: string;
}

export interface FilterSectionProps {
  selectedFilters: Array<FilterInfo>;
  handleFilterButtonClick: (id: number, categoryName: string) => React.ChangeEventHandler<HTMLInputElement>;
}

const SCROLL_DIST = 80;

const filterByCategory = (filters: Array<Filter> | undefined, categoryId: number) =>
  filters?.filter(filter => filter.category.id === categoryId) || [];

const FilterSection: React.FC<FilterSectionProps> = ({ selectedFilters, handleFilterButtonClick }) => {
  const sliderRef = useRef<HTMLElement>(null);

  const { data, isLoading, isError, error } = useQuery<FilterListQueryData, Error>('filters', getFilterList);

  const generationFilters = filterByCategory(data?.filters, 1);
  const areaFilters = filterByCategory(data?.filters, 2);
  const tagFilters = filterByCategory(data?.filters, 3);

  const handleLeftSlideButtonClick = () => {
    if (!sliderRef.current) {
      return;
    }
    sliderRef.current.scrollLeft -= SCROLL_DIST;
  };
  const handleRightSlideButtonClick = () => {
    if (!sliderRef.current) {
      return;
    }
    sliderRef.current.scrollLeft += SCROLL_DIST;
  };

  return (
    <S.FilterSectionContainer>
      <S.LeftButtonContainer>
        <SlideButton rightDirection={false} handleSlideButtonClick={handleLeftSlideButtonClick} />
      </S.LeftButtonContainer>
      <S.FilterSection ref={sliderRef}>
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
      </S.FilterSection>
      <S.RightButtonContainer>
        <SlideButton rightDirection handleSlideButtonClick={handleRightSlideButtonClick} />
      </S.RightButtonContainer>
    </S.FilterSectionContainer>
  );
};

export default FilterSection;
