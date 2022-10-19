import { type Story } from '@storybook/react';

import SectionTitle, { type SectionTitleProps } from '@shared/section-title/SectionTitle';

export default {
  title: 'Components/SectionTitle',
  component: SectionTitle,
};

const Template: Story<SectionTitleProps> = props => <SectionTitle {...props} />;

export const Default = Template.bind({});
Default.args = {
  children: '섹션 제목',
  align: 'center',
};
