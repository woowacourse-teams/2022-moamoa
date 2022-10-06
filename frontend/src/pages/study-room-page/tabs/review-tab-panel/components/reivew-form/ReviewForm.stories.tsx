import type { Story } from '@storybook/react';

import type { ReviewFormProps } from '@review-tab/components/reivew-form/ReviewForm';
import ReviewForm from '@review-tab/components/reivew-form/ReviewForm';

export default {
  title: 'Pages/StudyRoomPage/ReviewForm',
  component: ReviewForm,
  argTypes: {},
};

const Template: Story<ReviewFormProps> = props => <ReviewForm {...props} />;

export const Default = Template.bind({});
Default.args = {
  studyId: 1,
  author: {
    id: 1,
    username: 'your-name',
    profileUrl: '/',
    imageUrl:
      'https://images.unsplash.com/photo-1554151228-14d9def656e4?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60',
  },
};
