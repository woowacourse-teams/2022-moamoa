import { Story } from '@storybook/react';

import FilterButton from '@pages/main-page/filter-section/filter-button/FilterButton';
import type { FilterButtonProps } from '@pages/main-page/filter-section/filter-button/FilterButton';

export default {
  title: 'Components/FilterButton',
  component: FilterButton,
  argTypes: {
    shortName: { controls: 'text' },
    fullName: { controls: 'text' },
    isChecked: { controls: 'boolean' },
  },
};

const Template: Story<FilterButtonProps> = props => <FilterButton {...props} />;

export const Default = Template.bind({});
Default.args = {
  shortName: 'JS',
  fullName: '자바스크립트',
  isChecked: false,
};
Default.parameters = { controls: { exclude: ['handleFilterButtonClick'] } };

export const Clicked = Template.bind({});
Clicked.args = {
  shortName: 'JS',
  fullName: '자바스크립트',
  isChecked: true,
};
Clicked.parameters = { controls: { exclude: ['isChecked', 'handleFilterButtonClick'] } };

export const Unclicked = Template.bind({});
Unclicked.args = {
  shortName: 'JS',
  fullName: '자바스크립트',
  isChecked: false,
};
Unclicked.parameters = { controls: { exclude: ['isChecked', 'handleFilterButtonClick'] } };
