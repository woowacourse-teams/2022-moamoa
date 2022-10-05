import type { Story } from '@storybook/react';

import RightDirectionIcon from '@components/icons/right-direction-icon/RightDirectionIcon';

export default {
  title: 'Materials/Icons/RightDirectionIcon',
  component: RightDirectionIcon,
};

const Template: Story = () => <RightDirectionIcon />;

export const Default = Template.bind({});
Default.args = {};
