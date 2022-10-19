import { type Story } from '@storybook/react';
import { useState } from 'react';

import FilterButton, { type FilterButtonProps } from '@main-page/components/filter-button/FilterButton';

export default {
  title: 'Pages/MainPage/FilterButton',
  component: FilterButton,
  argTypes: {
    name: { controls: 'text' },
    description: { controls: 'text' },
    isChecked: { controls: 'boolean' },
  },
};

const Template: Story<FilterButtonProps> = props => {
  const [isChecked, setIsChecked] = useState(false);
  return <FilterButton {...props} isChecked={isChecked} onClick={() => setIsChecked(prev => !prev)} />;
};

export const Default = Template.bind({});
Default.args = {
  name: 'JS',
  description: '자바스크립트',
};
Default.parameters = { controls: { exclude: ['onClick', 'isChecked'] } };
