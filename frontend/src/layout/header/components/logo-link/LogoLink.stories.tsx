import { type Story } from '@storybook/react';

import LogoLink from '@layout/header/components/logo-link/LogoLink';

export default {
  title: 'Layout/LogoLink',
  component: LogoLink,
};

const Template: Story = props => <LogoLink />;

export const Default = Template.bind({});
Default.args = {};
