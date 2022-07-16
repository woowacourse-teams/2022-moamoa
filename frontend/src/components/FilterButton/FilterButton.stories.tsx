import { Story } from '@storybook/react';

import FilterButton from '@components/FilterButton/FilterButton';
import type { FilterButtonProps } from '@components/FilterButton/FilterButton';

export default {
  title: 'Components/FilterButton',
  component: FilterButton,
  argTypes: {
    shortTitle: { controls: 'text' },
    description: { controls: 'text' },
    isChecked: { controls: 'boolean' },
  },
};

const Template: Story<FilterButtonProps> = props => <FilterButton {...props} />;

export const Default = Template.bind({});
Default.args = {
  shortTitle: 'JS',
  description: '자바스크립트',
  isChecked: false,
};
Default.parameters = { controls: { exclude: ['handleFilterButtonClick'] } };

export const Clicked = Template.bind({});
Clicked.args = {
  shortTitle: 'JS',
  description: '자바스크립트',
  isChecked: true,
};
Clicked.parameters = { controls: { exclude: ['isChecked', 'handleFilterButtonClick'] } };

export const Unclicked = Template.bind({});
Unclicked.args = {
  shortTitle: 'JS',
  description: '자바스크립트',
  isChecked: false,
};
Unclicked.parameters = { controls: { exclude: ['isChecked', 'handleFilterButtonClick'] } };
