import { Story } from '@storybook/react';

import type { ReviewFormProps } from '@study-room-page/components/review-tab-panel/components/reivew-form/ReviewForm';
import ReviewForm from '@study-room-page/components/review-tab-panel/components/reivew-form/ReviewForm';

export default {
  title: 'Components/ReviewForm',
  component: ReviewForm,
  argTypes: {},
};

const Template: Story<ReviewFormProps> = props => <ReviewForm {...props} />;

export const Default = Template.bind({});
