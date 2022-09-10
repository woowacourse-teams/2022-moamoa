import type { Story } from '@storybook/react';

import LoginIcon from '@design/icons/login-icon/LoginIcon';

export default {
  title: 'Materials/Icons/LoginIcon',
  component: LoginIcon,
};

const Template: Story = () => <LoginIcon />;

export const Default = Template.bind({});
Default.args = {};
