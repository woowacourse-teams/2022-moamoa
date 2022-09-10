import type { Story } from '@storybook/react';

import RightUpArrowIcon from '@design/icons/right-up-arrow-icon/RightUpArrowIcon';

export default {
  title: 'Materials/Icons/RightUpArrowIcon',
  component: RightUpArrowIcon,
};

const Template: Story = () => <RightUpArrowIcon />;

export const Default = Template.bind({});
Default.args = {};
