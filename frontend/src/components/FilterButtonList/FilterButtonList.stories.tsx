import { Story } from '@storybook/react';
import { useState } from 'react';

import type { FilterButtonListProps } from './FilterButtonList';
import FilterButtonList from './FilterButtonList';

export default {
  title: 'Components/FilterButtonList',
  component: FilterButtonList,
};

const filters = [
  {
    id: 1,
    shortName: 'JS',
    description: '자바스크립트',
    category: {
      id: 3,
      name: 'tag',
    },
  },
  {
    id: 2,
    shortName: 'Java',
    description: '자바',
    category: {
      id: 3,
      name: 'tag',
    },
  },
  {
    id: 3,
    shortName: 'React',
    description: '리액트',
    category: {
      id: 3,
      name: 'tag',
    },
  },
  {
    id: 4,
    shortName: 'Spring',
    description: '스프링',
    category: {
      id: 3,
      name: 'tag',
    },
  },
  {
    id: 5,
    shortName: 'CS',
    description: '컴퓨터사이언스',
    category: {
      id: 3,
      name: 'tag',
    },
  },
  {
    id: 6,
    shortName: 'TS',
    description: '타입스크립트',
    category: {
      id: 3,
      name: 'tag',
    },
  },
  {
    id: 7,
    shortName: 'Review',
    description: '코드리뷰',
    category: {
      id: 3,
      name: 'tag',
    },
  },
];

const Template: Story<FilterButtonListProps> = () => {
  const [selectedFilters, setSelectedFilters] = useState<Array<{ id: number; categoryName: string }>>([]);

  const handleFilterButtonClick = (id: number, categoryName: string) => () => {
    setSelectedFilters(prev => {
      if (prev.some(filter => filter.id === id && filter.categoryName === categoryName)) {
        return prev.filter(filter => filter.id !== id || filter.categoryName !== categoryName);
      }
      return [...prev, { id, categoryName }];
    });
  };

  return (
    <FilterButtonList
      filters={filters}
      selectedFilters={selectedFilters}
      handleFilterButtonClick={handleFilterButtonClick}
    />
  );
};

export const Default = Template.bind({});