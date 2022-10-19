import { type Story } from '@storybook/react';
import { useState } from 'react';

import type { CategoryName, Tag, TagInfo } from '@custom-types';

import FilterButtonList, {
  type FilterButtonListProps,
} from '@main-page/components/filter-button-list/FilterButtonList';

export default {
  title: 'Pages/MainPage/FilterButtonList',
  component: FilterButtonList,
};

const filters: Array<Tag> = [
  {
    id: 1,
    name: 'JS',
    description: '자바스크립트',
    category: {
      id: 3,
      name: 'subject',
    },
  },
  {
    id: 2,
    name: 'Java',
    description: '자바',
    category: {
      id: 3,
      name: 'subject',
    },
  },
  {
    id: 3,
    name: 'React',
    description: '리액트',
    category: {
      id: 3,
      name: 'subject',
    },
  },
  {
    id: 4,
    name: 'Spring',
    description: '스프링',
    category: {
      id: 3,
      name: 'subject',
    },
  },
  {
    id: 5,
    name: 'CS',
    description: '컴퓨터과학',
    category: {
      id: 3,
      name: 'subject',
    },
  },
  {
    id: 6,
    name: 'TS',
    description: '타입스크립트',
    category: {
      id: 3,
      name: 'subject',
    },
  },
  {
    id: 7,
    name: 'Review',
    description: '코드리뷰',
    category: {
      id: 3,
      name: 'subject',
    },
  },
];

const Template: Story<FilterButtonListProps> = () => {
  const [selectedFilters, setSelectedFilters] = useState<Array<TagInfo>>([]);

  const handleFilterButtonClick = (id: number, categoryName: CategoryName) => () => {
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
      onFilterButtonClick={handleFilterButtonClick}
    />
  );
};

export const Default = Template.bind({});
