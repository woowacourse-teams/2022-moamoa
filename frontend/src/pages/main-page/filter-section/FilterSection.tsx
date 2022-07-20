import { useRef } from 'react';
import { useQuery } from 'react-query';

import type { Tag, TagInfo, TagListQueryData } from '@custom-types/index';

import { getTagList } from '@api/getTagList';

import * as S from '@pages/main-page/filter-section/FilterSection.style';
import FilterButtonList from '@pages/main-page/filter-section/filter-button-list/FilterButtonList';

import ArrowButton from '@components/arrow-button/ArrowButton';

export interface FilterSectionProps {
  selectedFilters: Array<TagInfo>;
  handleFilterButtonClick: (id: number, categoryName: string) => React.MouseEventHandler<HTMLButtonElement>;
}

const SCROLL_DIST = 100;

const filterByCategory = (tags: Array<Tag> | undefined, categoryId: number) =>
  tags?.filter(tag => tag.category.id === categoryId) || [];

const FilterSection: React.FC<FilterSectionProps> = ({ selectedFilters, handleFilterButtonClick }) => {
  const sliderRef = useRef<HTMLElement>(null);

  const { data, isLoading, isError, error } = useQuery<TagListQueryData, Error>('filters', getTagList);

  const generationTags = filterByCategory(data?.tags, 1);
  const areaTags = filterByCategory(data?.tags, 2);
  const subjectTags = filterByCategory(data?.tags, 3);

  const handleLeftSlideButtonClick = () => {
    if (!sliderRef.current) {
      return;
    }

    const slider = sliderRef.current;
    if (slider.scrollLeft === 0) {
      sliderRef.current.scrollLeft = slider.scrollWidth - slider.clientWidth;
      return;
    }
    sliderRef.current.scrollLeft -= SCROLL_DIST;
  };

  const handleRightSlideButtonClick = () => {
    if (!sliderRef.current) return;

    const slider = sliderRef.current;
    if (slider.scrollLeft === slider.scrollWidth - slider.clientWidth) {
      sliderRef.current.scrollLeft = 0;
      return;
    }
    sliderRef.current.scrollLeft += SCROLL_DIST;
  };

  return (
    <S.FilterSectionContainer>
      <S.LeftButtonContainer>
        <ArrowButton direction="left" ariaLabel="왼쪽으로 스크롤" handleSlideButtonClick={handleLeftSlideButtonClick} />
      </S.LeftButtonContainer>
      <S.FilterSection ref={sliderRef}>
        {isLoading && <div>로딩 중...</div>}
        {isError && <div>{error.message}</div>}
        <FilterButtonList
          filters={areaTags}
          selectedFilters={selectedFilters}
          handleFilterButtonClick={handleFilterButtonClick}
        />
        <S.VerticalLine />
        <FilterButtonList
          filters={generationTags}
          selectedFilters={selectedFilters}
          handleFilterButtonClick={handleFilterButtonClick}
        />
        <S.VerticalLine />
        <FilterButtonList
          filters={subjectTags}
          selectedFilters={selectedFilters}
          handleFilterButtonClick={handleFilterButtonClick}
        />
      </S.FilterSection>
      <S.RightButtonContainer>
        <ArrowButton
          direction="right"
          ariaLabel="오른쪽으로 스크롤"
          handleSlideButtonClick={handleRightSlideButtonClick}
        />
      </S.RightButtonContainer>
    </S.FilterSectionContainer>
  );
};

export default FilterSection;