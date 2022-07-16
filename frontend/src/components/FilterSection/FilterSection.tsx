import FilterButton from '@components/FilterButton/FilterButton';

import * as S from './FilterSection.style';

export interface FilterInfo {
  id: number;
  categoryName: string;
}

export interface FilterSectionProps {
  selectedFilters: Array<FilterInfo>;
  handleFilterButtonClick: (id: number, categoryName: string) => React.ChangeEventHandler<HTMLInputElement>;
}

const areaFilters = [
  {
    id: 1,
    shortName: 'FE',
    description: '프론트엔드',
    categoryName: 'area',
  },
  {
    id: 2,
    shortName: 'BE',
    description: '백엔드',
    categoryName: 'area',
  },
];

const generationFilters = [
  {
    id: 1,
    shortName: '4기',
    description: '우테코4기',
    categoryName: 'generation',
  },
  {
    id: 2,
    shortName: '3기',
    description: '우테코3기',
    categoryName: 'generation',
  },
];

const tagFilters = [
  {
    id: 1,
    shortName: 'JS',
    description: '자바스크립트',
    categoryName: 'tag',
  },
  {
    id: 2,
    shortName: 'Java',
    description: '자바',
    categoryName: 'tag',
  },
  {
    id: 3,
    shortName: 'React',
    description: '리액트',
    categoryName: 'tag',
  },
  {
    id: 4,
    shortName: 'Spring',
    description: '스프링',
    categoryName: 'tag',
  },
  {
    id: 5,
    shortName: 'CS',
    description: '컴퓨터사이언스',
    categoryName: 'tag',
  },
  {
    id: 6,
    shortName: 'TS',
    description: '타입스크립트',
    categoryName: 'tag',
  },
  {
    id: 7,
    shortName: 'Review',
    description: '코드리뷰',
    categoryName: 'tag',
  },
];

const isSelected = (id: number, categoryName: string, selectedFilters: Array<{ id: number; categoryName: string }>) =>
  selectedFilters.some(filter => filter.id === id && filter.categoryName === categoryName);

const FilterSection: React.FC<FilterSectionProps> = ({ selectedFilters, handleFilterButtonClick }) => {
  return (
    <S.FilterSectionContainer>
      <S.FilterSectionHeader>필터</S.FilterSectionHeader>
      <S.FilterButtons>
        {areaFilters.map(({ id, shortName, description, categoryName }) => (
          <li key={id}>
            <FilterButton
              shortTitle={shortName}
              description={description}
              isChecked={isSelected(id, categoryName, selectedFilters)}
              handleFilterButtonClick={handleFilterButtonClick(id, categoryName)}
            />
          </li>
        ))}
      </S.FilterButtons>
      <S.VerticalLine />
      <S.FilterButtons>
        {generationFilters.map(({ id, shortName, description, categoryName }) => (
          <li key={id}>
            <FilterButton
              shortTitle={shortName}
              description={description}
              isChecked={isSelected(id, categoryName, selectedFilters)}
              handleFilterButtonClick={handleFilterButtonClick(id, categoryName)}
            />
          </li>
        ))}
      </S.FilterButtons>
      <S.VerticalLine />
      <S.FilterButtons>
        {tagFilters.map(({ id, shortName, description, categoryName }) => (
          <li key={id}>
            <FilterButton
              shortTitle={shortName}
              description={description}
              isChecked={isSelected(id, categoryName, selectedFilters)}
              handleFilterButtonClick={handleFilterButtonClick(id, categoryName)}
            />
          </li>
        ))}
      </S.FilterButtons>
    </S.FilterSectionContainer>
  );
};

export default FilterSection;
