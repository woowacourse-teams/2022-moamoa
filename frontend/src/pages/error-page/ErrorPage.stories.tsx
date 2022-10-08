import { type Story } from '@storybook/react';

import { ErrorPage } from '@pages';

import PageWrapper from '@components/page-wrapper/PageWrapper';

export default {
  title: 'Pages/ErrorPage',
  component: ErrorPage,
};

const Template: Story = () => (
  <PageWrapper>
    <ErrorPage />
  </Wrapper>
);

export const Default = Template.bind({});
Default.args = {};
