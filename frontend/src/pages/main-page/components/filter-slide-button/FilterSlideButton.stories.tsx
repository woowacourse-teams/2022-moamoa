import type { Story } from '@storybook/react';

import FilterSlideButton, {
  type FilterSlideButtonProps,
} from '@main-page/components/filter-slide-button/FilterSlideButton';

export default {
  title: 'Pages/MainPage/FilterSlideButton',
  component: FilterSlideButton,
  argTypes: {
    direction: { controls: 'text' },
  },
};

const Template: Story<FilterSlideButtonProps> = props => (
  <div style={{ position: 'relative', width: '50px', height: '50px' }}>
    <FilterSlideButton {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  direction: 'right',
};
