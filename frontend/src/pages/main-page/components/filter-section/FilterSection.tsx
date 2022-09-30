import { useRef } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { CategoryName, Tag, TagId, TagInfo } from '@custom-types';

import { mqDown } from '@styles/responsive';

import { useGetTags } from '@api/tags';

import Divider from '@components/divider/Divider';

import FilterButtonList from '@main-page/components/filter-button-list/FilterButtonList';
import FilterSlideButton from '@main-page/components/filter-slide-button/FilterSlideButton';

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
    <Self>
      <LeftButtonContainer>
        <FilterSlideButton direction="left" ariaLabel="왼쪽으로 스크롤" onClick={handleLeftSlideButtonClick} />
      </LeftButtonContainer>
      <Filter ref={sliderRef}>
        {isLoading && <div>로딩 중...</div>}
        {isError && <div>필터 불러오기에 실패했습니다.</div>}
        <FilterButtonList
          filters={areaTags}
          selectedFilters={selectedFilters}
          onFilterButtonClick={handleFilterButtonClick}
        />
        <Divider orientation="vertical" verticalLength="40px" space={0} />
        <FilterButtonList
          filters={generationTags}
          selectedFilters={selectedFilters}
          onFilterButtonClick={handleFilterButtonClick}
        />
        <Divider orientation="vertical" verticalLength="40px" />
        <FilterButtonList
          filters={subjectTags}
          selectedFilters={selectedFilters}
          onFilterButtonClick={handleFilterButtonClick}
        />
      </Filter>
      <RightButtonContainer>
        <FilterSlideButton direction="right" ariaLabel="오른쪽으로 스크롤" onClick={handleRightSlideButtonClick} />
      </RightButtonContainer>
    </Self>
  );
};

const Self = styled.div`
  ${({ theme }) => css`
    position: sticky;
    top: 85px;
    z-index: 1;

    max-width: 1280px;
    margin: 0 auto 16px;
    padding: 16px 20px 0;

    background-color: ${theme.colors.secondary.light};
    border-bottom: 1px solid ${theme.colors.secondary.dark};
  `}

  ${mqDown('md')} {
    top: 70px;
  }

  ${mqDown('sm')} {
    top: 60px;
    padding: 16px 5px 0;
  }
`;

const RightButtonContainer = styled.div`
  ${({ theme }) => css`
    display: flex;
    align-items: center;
    justify-content: center;

    position: absolute;
    top: 0;
    right: 20px;
    z-index: 2;

    height: 100%;
    padding: auto 0;

    background-color: ${theme.colors.secondary.light}66;
  `}
`;

const LeftButtonContainer = styled(RightButtonContainer)`
  right: calc(100% - 20px - 24px);
`;

const Filter = styled.section`
  display: flex;
  align-items: center;
  column-gap: 32px;

  padding: 16px auto 4px;
  margin: 0 20px 0 12px;
  overflow-x: auto;
  scroll-behavior: smooth;

  &::-webkit-scrollbar {
    display: none;
  }

  ${mqDown('sm')} {
    column-gap: 16px;
  }
`;

export default FilterSection;
