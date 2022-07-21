import { Story } from '@storybook/react';

import StudyReviewCard from '@detail-page/components/study-review-card/StudyReviewCard';
import type { StudyReviewCardProps } from '@detail-page/components/study-review-card/StudyReviewCard';

export default {
  title: 'Components/StudyReviewCard',
  component: StudyReviewCard,
};

const Template: Story<StudyReviewCardProps> = props => (
  <div style={{ width: '300px' }}>
    <StudyReviewCard {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  imageUrl:
    'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
  username: 'moamoa',
  reviewDate: '2020-07-19',
  review: '너무 좋아요~~ 짱짱',
};
