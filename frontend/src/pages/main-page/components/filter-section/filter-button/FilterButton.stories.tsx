import type { Story } from '@storybook/react';

import FilterButton from '@main-page/components/filter-section/filter-button/FilterButton';
import type { FilterButtonProps } from '@main-page/components/filter-section/filter-button/FilterButton';

export default {
  title: 'Components/FilterButton',
  component: FilterButton,
  argTypes: {
    name: { controls: 'text' },
    description: { controls: 'text' },
    isChecked: { controls: 'boolean' },
  },
};

const Template: Story<FilterButtonProps> = props => <FilterButton {...props} />;

export const Default = Template.bind({});
Default.args = {
  name: 'JS',
  description: '자바스크립트',
  isChecked: false,
};
Default.parameters = { controls: { exclude: ['onFilterButtonClick'] } };

export const Clicked = Template.bind({});
Clicked.args = {
  name: 'JS',
  description: '자바스크립트',
  isChecked: true,
};
Clicked.parameters = { controls: { exclude: ['isChecked', 'onFilterButtonClick'] } };

export const Unclicked = Template.bind({});
Unclicked.args = {
  name: 'JS',
  description: '자바스크립트',
  isChecked: false,
};
Unclicked.parameters = { controls: { exclude: ['isChecked', 'onFilterButtonClick'] } };
