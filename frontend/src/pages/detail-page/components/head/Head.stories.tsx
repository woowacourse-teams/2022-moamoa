import type { Story } from '@storybook/react';

import Head from '@detail-page/components/head/Head';
import type { HeadProps } from '@detail-page/components/head/Head';

export default {
  title: 'Components/Head',
  component: Head,
};

const Template: Story<HeadProps> = props => (
  <div style={{ width: '400px' }}>
    <Head {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  title: '2022-모아모아',
  recruitmentStatus: 'RECRUITMENT_START',
  startDate: '2022-07-18',
  endDate: '2022-07-18',
  excerpt: '모아모아 최고~~',
  tags: [
    { id: 1, name: 'CS' },
    { id: 2, name: 'JavaScript' },
    { id: 3, name: 'React' },
    { id: 4, name: 'Spring' },
    { id: 5, name: 'TypeScript' },
    { id: 6, name: 'Network' },
    { id: 7, name: 'JPA' },
  ],
};
