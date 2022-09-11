import type { Story } from '@storybook/react';

import SlideButton from '@main-page/components/filter-slide-button/FilterSlideButton';
import type { SlideButtonProps } from '@main-page/components/filter-slide-button/FilterSlideButton';

export default {
  title: 'Components/SlideButton',
  component: SlideButton,
  argTypes: {
    direction: { controls: 'text' },
  },
};

const Template: Story<SlideButtonProps> = props => (
  <div style={{ position: 'relative', width: '50px', height: '50px' }}>
    <SlideButton {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  direction: 'right',
};
