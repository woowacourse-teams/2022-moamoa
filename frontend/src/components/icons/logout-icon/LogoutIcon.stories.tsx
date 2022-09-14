import type { Story } from '@storybook/react';

import LogoutIcon from '@components/icons/logout-icon/LogoutIcon';

export default {
  title: 'Materials/Icons/LogoutIcon',
  component: LogoutIcon,
};

const Template: Story = () => <LogoutIcon />;

export const Default = Template.bind({});
Default.args = {};
