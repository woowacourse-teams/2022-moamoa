import { Story } from '@storybook/react';

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
  status: 'OPEN',
  countOfReviews: 10,
  startDate: '2022-07-18',
  endDate: '2022-07-18',
  excerpt: '모아모아 최고~~',
  tags: [
    { id: 1, tagName: 'CS' },
    { id: 2, tagName: 'JavaScript' },
    { id: 3, tagName: 'React' },
    { id: 4, tagName: 'Spring' },
    { id: 5, tagName: 'TypeScript' },
    { id: 6, tagName: 'Network' },
    { id: 7, tagName: 'JPA' },
  ],
};
