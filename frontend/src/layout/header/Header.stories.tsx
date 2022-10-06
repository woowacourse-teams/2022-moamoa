import type { Story } from '@storybook/react';

import Header from '@layout/header/Header';

export default {
  title: 'Layout/Header',
  component: Header,
};

const Template: Story = () => <Header />;

export const Default = Template.bind({});
Default.args = {};
