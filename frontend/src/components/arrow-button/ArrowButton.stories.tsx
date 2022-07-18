import { Story } from '@storybook/react';

import SlideButton, { SlideButtonProps } from '@components/arrow-button/ArrowButton';

export default {
  title: 'Components/SlideButton',
  component: SlideButton,
  argTypes: {
    rightDirection: { controls: 'boolean' },
  },
};

const Template: Story<SlideButtonProps> = props => (
  <div style={{ position: 'relative', width: '50px', height: '50px' }}>
    <SlideButton {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  rightDirection: false,
};
