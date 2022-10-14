import { type Story } from '@storybook/react';

import DownArrowIcon from '@shared/icons/down-arrow-icon/DownArrowIcon';

export default {
  title: 'Materials/Icons/DownArrowIcon',
  component: DownArrowIcon,
};

const Template: Story = () => <DownArrowIcon />;

export const Default = Template.bind({});
Default.args = {};
