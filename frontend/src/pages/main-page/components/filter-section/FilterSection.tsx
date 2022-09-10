import { useRef } from 'react';

import type { CategoryName, Tag, TagId, TagInfo } from '@custom-types';

import { useGetTags } from '@api/tags';

import ArrowButton from '@components/arrow-button/ArrowButton';

import FilterButtonList from '@main-page/components/filter-button-list/FilterButtonList';
import * as S from '@main-page/components/filter-section/FilterSection.style';

export type FilterSectionProps = {
  selectedFilters: Array<TagInfo>;
  onFilterButtonClick: (id: TagId, categoryName: CategoryName) => React.MouseEventHandler<HTMLButtonElement>;
};

const SCROLL_DIST = 100;

const filterByCategory = (tags: Array<Tag> | undefined, categoryId: number) =>
  tags?.filter(tag => tag.category.id === categoryId) || [];

const FilterSection: React.FC<FilterSectionProps> = ({
  selectedFilters,
  onFilterButtonClick: handleFilterButtonClick,
}) => {
  const sliderRef = useRef<HTMLElement>(null);

  const { data, isLoading, isError } = useGetTags();

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
        <ArrowButton direction="left" ariaLabel="왼쪽으로 스크롤" onSlideButtonClick={handleLeftSlideButtonClick} />
      </S.LeftButtonContainer>
      <S.FilterSection ref={sliderRef}>
        {isLoading && <div>로딩 중...</div>}
        {isError && <div>필터 불러오기에 실패했습니다.</div>}
        <FilterButtonList
          filters={areaTags}
          selectedFilters={selectedFilters}
          onFilterButtonClick={handleFilterButtonClick}
        />
        <S.VerticalLine />
        <FilterButtonList
          filters={generationTags}
          selectedFilters={selectedFilters}
          onFilterButtonClick={handleFilterButtonClick}
        />
        <S.VerticalLine />
        <FilterButtonList
          filters={subjectTags}
          selectedFilters={selectedFilters}
          onFilterButtonClick={handleFilterButtonClick}
        />
      </S.FilterSection>
      <S.RightButtonContainer>
        <ArrowButton direction="right" ariaLabel="오른쪽으로 스크롤" onSlideButtonClick={handleRightSlideButtonClick} />
      </S.RightButtonContainer>
    </S.FilterSectionContainer>
  );
};

export default FilterSection;
