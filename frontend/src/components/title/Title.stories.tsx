import type { Story } from '@storybook/react';

import Title, { type PageTitleProps, type SectionTitleProps } from '@components/title/Title';

export default {
  title: 'Components/Title',
  component: Title,
};

const PageTitleTemplate: Story<PageTitleProps> = props => <Title.Page {...props} />;

export const PageTitle = PageTitleTemplate.bind({});
PageTitle.args = {
  children: '페이지 제목',
  align: 'center',
};

const SectionTitleTemplate: Story<SectionTitleProps> = props => <Title.Section {...props} />;

export const SectionTitle = SectionTitleTemplate.bind({});
SectionTitle.args = {
  children: '섹션 제목',
  align: 'center',
};
