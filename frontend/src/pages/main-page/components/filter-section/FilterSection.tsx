import { useRef } from 'react';

import { type Theme, css, useTheme } from '@emotion/react';
import styled from '@emotion/styled';

import { type CategoryName, type Tag, type TagId, type TagInfo } from '@custom-types';

import { mqDown } from '@styles/responsive';

import { useGetTags } from '@api/tags';

import Divider from '@components/divider/Divider';

import FilterButtonList, {
  type FilterButtonListProps,
} from '@main-page/components/filter-button-list/FilterButtonList';
import ImportedFilterSlideButton, {
  type FilterSlideButtonProps as ImportedFilterSlideButtonProps,
} from '@main-page/components/filter-slide-button/FilterSlideButton';

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
  const theme = useTheme();
  const sliderRef = useRef<HTMLDivElement>(null);

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
      <FilterSlideButton
        theme={theme}
        direction="left"
        ariaLabel="왼쪽으로 스크롤"
        onClick={handleLeftSlideButtonClick}
      />
      <FilterListContainer ref={sliderRef}>
        {isLoading && <Loading />}
        {isError && <Error />}
        <AreaTagList
          filters={areaTags}
          selectedFilters={selectedFilters}
          onFilterButtonClick={handleFilterButtonClick}
        />
        <Divider orientation="vertical" verticalLength="40px" space={0} />
        <GenerationTagList
          filters={generationTags}
          selectedFilters={selectedFilters}
          onFilterButtonClick={handleFilterButtonClick}
        />
        <Divider orientation="vertical" verticalLength="40px" />
        <SubjectTagList
          filters={subjectTags}
          selectedFilters={selectedFilters}
          onFilterButtonClick={handleFilterButtonClick}
        />
      </FilterListContainer>
      <FilterSlideButton
        theme={theme}
        direction="right"
        ariaLabel="오른쪽으로 스크롤"
        onClick={handleRightSlideButtonClick}
      />
    </Self>
  );
};

const Self = styled.section`
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

type FilterSlideButtonProps = { theme: Theme } & ImportedFilterSlideButtonProps;
const FilterSlideButton: React.FC<FilterSlideButtonProps> = ({ theme, direction, ...props }) => {
  const style = css`
    display: flex;
    justify-content: center;
    align-items: center;

    position: absolute;
    top: 0;
    right: ${direction === 'left' ? 'calc(100% - 20px - 24px)' : '20px'};
    z-index: 2;

    height: 100%;
    padding: 0;

    background-color: ${theme.colors.secondary.light}66;
  `;
  return (
    <div css={style}>
      <ImportedFilterSlideButton direction={direction} {...props} />
    </div>
  );
};

const FilterListContainer = styled.div`
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

type AreaTagListProps = FilterButtonListProps;
const AreaTagList: React.FC<AreaTagListProps> = ({
  filters,
  selectedFilters,
  onFilterButtonClick: handleFilterButtonClick,
}) => (
  <FilterButtonList filters={filters} selectedFilters={selectedFilters} onFilterButtonClick={handleFilterButtonClick} />
);

type GenerationTagListProps = FilterButtonListProps;
const GenerationTagList: React.FC<GenerationTagListProps> = ({
  filters,
  selectedFilters,
  onFilterButtonClick: handleFilterButtonClick,
}) => (
  <FilterButtonList filters={filters} selectedFilters={selectedFilters} onFilterButtonClick={handleFilterButtonClick} />
);

type SubjectTagListProps = FilterButtonListProps;
const SubjectTagList: React.FC<SubjectTagListProps> = ({
  filters,
  selectedFilters,
  onFilterButtonClick: handleFilterButtonClick,
}) => (
  <FilterButtonList filters={filters} selectedFilters={selectedFilters} onFilterButtonClick={handleFilterButtonClick} />
);

const Loading = () => <div>Loading...</div>;

const Error = () => <div>필터 불러오기에 실패했습니다.</div>;

export default FilterSection;
