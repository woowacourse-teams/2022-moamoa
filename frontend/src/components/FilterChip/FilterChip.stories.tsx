import { Story } from '@storybook/react';

import FilterChip from './FilterChip';
import type { FilterChipProps } from './FilterChip';

export default {
  title: 'Components/FilterChip',
  component: FilterChip,
  argTypes: {
    children: { controls: 'text' },
  },
};

const Template: Story<FilterChipProps> = props => (
  <FilterChip
    {...props}
    handleCloseButtonClick={() => {
      alert('클릭 이벤트');
    }}
  />
);

export const Default = Template.bind({});
Default.args = {
  children: 'FE',
};
