import { ErrorPage } from '@pages';
import type { Story } from '@storybook/react';

import Wrapper from '@components/wrapper/Wrapper';

export default {
  title: 'Pages/ErrorPage',
  component: ErrorPage,
};

const Template: Story = () => (
  <Wrapper>
    <ErrorPage />
  </Wrapper>
);

export const Default = Template.bind({});
Default.args = {};
