import { Story } from '@storybook/react';

import Header from '@components/Header';
// TODO: 삭제
import Wrapper from '@components/Wrapper';

export default {
  title: 'Components/Header',
  component: Header,
};

const Template: Story = props => <Header {...props} />;

export const Default = Template.bind({});
Default.args = {};
