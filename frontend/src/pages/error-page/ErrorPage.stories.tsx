import { type Story } from '@storybook/react';

import { ErrorPage } from '@pages';

import PageWrapper from '@shared/page-wrapper/PageWrapper';

export default {
  title: 'Pages/ErrorPage',
  component: ErrorPage,
};

const Template: Story = () => (
  <PageWrapper>
    <ErrorPage />
  </PageWrapper>
);

export const Default = Template.bind({});
Default.args = {};
