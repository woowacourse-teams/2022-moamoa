import type { Story } from '@storybook/react';

import SlideButton from '@components/arrow-button/ArrowButton';
import type { SlideButtonProps } from '@components/arrow-button/ArrowButton';

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
