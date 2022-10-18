import { type Story } from '@storybook/react';

import Chip, { type ChipProps } from '@shared/chip/Chip';

export default {
  title: 'Components/Chip',
  component: Chip,
};

const Template: Story<ChipProps> = props => <Chip {...props} />;

export const Default = Template.bind({});
Default.args = {
  children: '모집중',
  variant: 'primary',
};
