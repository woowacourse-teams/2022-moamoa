import { Story } from '@storybook/react';
import { useState } from 'react';

import FilterSection, { FilterSectionProps } from '@components/FilterSection/FilterSection';

export default {
  title: 'Components/FilterSection',
  component: FilterSection,
};

const Template: Story<FilterSectionProps> = () => {
  const [selectedFilters, setSelectedFilters] = useState<Array<{ id: number; categoryName: string }>>([]);

  const handleFilterButtonClick = (id: number, categoryName: string) => () => {
    setSelectedFilters(prev => {
      if (prev.some(filter => filter.id === id && filter.categoryName === categoryName)) {
        return prev.filter(filter => filter.id !== id || filter.categoryName !== categoryName);
      }
      return [...prev, { id, categoryName }];
    });
  };

  return <FilterSection selectedFilters={selectedFilters} handleFilterButtonClick={handleFilterButtonClick} />;
};

export const Default = Template.bind({});
