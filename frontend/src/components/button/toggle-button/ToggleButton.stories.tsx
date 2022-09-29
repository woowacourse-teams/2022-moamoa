import type { Story } from '@storybook/react';
import { useState } from 'react';

import ToggleButton, { type ToggleButtonProps } from '@components/button/toggle-button/ToggleButton';

export default {
  title: 'Components/ToggleButton',
  component: ToggleButton,
  argTypes: {
    children: { controls: 'text' },
  },
};

const Template: Story<ToggleButtonProps> = props => {
  const [isChecked, setIsChecked] = useState(false);

  return <ToggleButton {...props} checked={isChecked} onClick={() => setIsChecked(prev => !prev)} />;
};

export const Default = Template.bind({});
Default.args = {
  children: '➕ hihi',
  fluid: false,
};
Default.parameters = { controls: { exclude: ['checked', 'onClick'] } };
