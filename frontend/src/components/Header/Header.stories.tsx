import { Story } from '@storybook/react';

import Header from '@components/Header';
import Wrapper from '@components/Wrapper';

export default {
  title: 'Components/Header',
  component: Header,
};

const Template: Story = props => <Header {...props} />;

export const Default = Template.bind({});
Default.args = {};
