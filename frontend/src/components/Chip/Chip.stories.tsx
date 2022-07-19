import { Story } from '@storybook/react';

import type { ChipProps } from '@components/chip/Chip';
import Chip from '@components/chip/Chip';

export default {
  title: 'Components/Chip',
  component: Chip,
  argTypes: {
    children: { controls: 'text' },
    disabled: { controls: 'boolean' },
  },
};

const Template: Story<ChipProps> = props => <Chip {...props} />;

export const Default = Template.bind({});
Default.args = {
  children: '모집중',
  disabled: false,
};

export const ActiveChip = Template.bind({});
ActiveChip.args = {
  children: '모집중',
  disabled: false,
};
ActiveChip.parameters = { controls: { exclude: ['disabled'] } };

export const DisabledChip = Template.bind({});
DisabledChip.args = {
  children: '모집완료',
  disabled: true,
};
DisabledChip.parameters = { controls: { exclude: ['disabled'] } };
