import type { Story } from '@storybook/react';

import PageTitle, { type PageTitleProps } from '@design/components/page-title/PageTitle';

export default {
  title: 'Design/Components/PageTitle',
  component: PageTitle,
};

const Template: Story<PageTitleProps> = props => <PageTitle {...props} />;

export const Default = Template.bind({});
Default.args = {
  children: '페이지 제목',
  align: 'center',
};
