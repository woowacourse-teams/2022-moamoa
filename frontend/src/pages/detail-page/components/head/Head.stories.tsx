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
    {
      id: 1,
      name: 'CS',
      description: '컴퓨터과학',
      category: {
        id: 1,
        name: 'subject',
      },
    },
    {
      id: 2,
      name: 'JavaScript',
      description: '컴퓨터과학',
      category: {
        id: 1,
        name: 'subject',
      },
    },
    {
      id: 3,
      name: 'React',
      description: '컴퓨터과학',
      category: {
        id: 1,
        name: 'subject',
      },
    },
    {
      id: 4,
      name: 'Spring',
      description: '컴퓨터과학',
      category: {
        id: 1,
        name: 'subject',
      },
    },
    {
      id: 5,
      name: 'TypeScript',
      description: '컴퓨터과학',
      category: {
        id: 1,
        name: 'subject',
      },
    },
    {
      id: 6,
      name: 'Network',
      description: '컴퓨터과학',
      category: {
        id: 1,
        name: 'subject',
      },
    },
    {
      id: 7,
      name: 'JPA',
      description: '컴퓨터과학',
      category: {
        id: 1,
        name: 'subject',
      },
    },
  ],
};
