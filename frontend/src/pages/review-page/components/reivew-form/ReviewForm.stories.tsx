import type { ReviewFormProps } from '@review-page/components/reivew-form/ReviewForm';
import ReviewForm from '@review-page/components/reivew-form/ReviewForm';
import { Story } from '@storybook/react';

export default {
  title: 'Components/ReviewForm',
  component: ReviewForm,
  argTypes: {},
};

const Template: Story<ReviewFormProps> = props => <ReviewForm {...props} />;

export const Default = Template.bind({});
