import { Story } from '@storybook/react';

import Header from '@layout/header/Header';

export default {
  title: 'Components/Header',
  component: Header,
};

const Template: Story = props => <Header {...props} />;

export const Default = Template.bind({});
Default.args = {};
