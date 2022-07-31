import { Story } from '@storybook/react';

import ReviewComment from '@pages/review-page/components/review-comment/ReviewComment';
import type { ReviewCommentProps } from '@pages/review-page/components/review-comment/ReviewComment';

export default {
  title: 'Components/ReviewComment',
  component: ReviewComment,
  argTypes: {},
};

const Template: Story<ReviewCommentProps> = props => <ReviewComment {...props} />;

export const Default = Template.bind({
  id: '123',
});
