import { Story } from '@storybook/react';
import { useState } from 'react';

import { filters } from '@components/DropBox/DropBox.stories';
import Filter from '@components/Filter/Filter';

export default {
  title: 'Components/Filter',
  component: Filter,
};

const Template: Story = () => {
  const [selectedFilters, setSelectedFilters] = useState<Array<{ id: number }>>([]);

  const handleFilterItemClick = (selectedId: number) => () => {
    setSelectedFilters(prev => {
      if (prev.some(({ id }) => selectedId === id)) {
        return prev.filter(({ id }) => selectedId !== id);
      }
      return [...prev, { id: selectedId }];
    });
  };

  return <Filter filters={filters} selectedFilters={selectedFilters} handleFilterItemClick={handleFilterItemClick} />;
};

export const Default = Template.bind({});
