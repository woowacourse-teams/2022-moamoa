import { Story } from '@storybook/react';

import MainPage from '@pages/MainPage';

import Wrapper from '@components/Wrapper';

export default {
  title: 'Pages/MainPage',
  component: MainPage,
};

const Template: Story = () => (
  <Wrapper>
    <MainPage />
  </Wrapper>
);

export const Default = Template.bind({});
Default.args = {};
