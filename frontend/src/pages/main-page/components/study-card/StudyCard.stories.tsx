import { type Story } from '@storybook/react';

import StudyCard, { type StudyCardProps } from '@main-page/components/study-card/StudyCard';

export default {
  title: 'Pages/MainPage/StudyCard',
  component: StudyCard,
};

const Template: Story<StudyCardProps> = props => (
  <div style={{ width: '256px' }}>
    <StudyCard {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  thumbnailUrl:
    'https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1673&q=80',
  thumbnailAlt: '스터디 이미지 Alt',
  title: '자바스크립트 스터디',
  excerpt: '자바스크립트 스터디입니다',
  tags: [
    {
      id: 1,
      name: 'FE',
    },
    { id: 2, name: '4기' },
    { id: 3, name: 'JS' },
  ],
  isOpen: true,
};
