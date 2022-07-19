import { Story } from '@storybook/react';

import Logo from '@layout/header/logo/Logo';

export default {
  title: 'Components/Logo',
  component: Logo,
};

const Template: Story = props => <Logo {...props} />;

export const Default = Template.bind({});
Default.args = {};
