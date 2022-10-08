import { type Story } from '@storybook/react';

import MyStudyCard, { type MyStudyCardProps } from '@my-study-page/components/my-study-card/MyStudyCard';

export default {
  title: 'Pages/MyStudyPage/MyStudyCard',
  component: MyStudyCard,
};

const Template: Story<MyStudyCardProps> = props => <MyStudyCard {...props} />;

export const Default = Template.bind({});
Default.args = {
  title: '2022 모아모아 스터디',
  ownerName: 'airman5573',
  tags: [
    {
      id: 1,
      name: 'Java',
    },
    {
      id: 2,
      name: 'JS',
    },
    {
      id: 3,
      name: 'FE',
    },
  ],
  startDate: '2022-08-13',
  endDate: '2022-08-20',
  done: false,
};
