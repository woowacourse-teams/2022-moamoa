import { Story } from '@storybook/react';

import MainPage from '@pages/main-page/MainPage';

import Wrapper from '@components/wrapper/Wrapper';

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
