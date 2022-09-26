import type { Story } from '@storybook/react';

import tw from '@utils/tw';

import Category from '@create-study-page/components/category/Category';
import type { CategoryProps } from '@create-study-page/components/category/Category';

export default {
  title: 'Components/Category',
  component: Category,
};

const Template: Story<CategoryProps> = props => (
  <div css={tw`max-w-[400px]`}>
    <Category {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = {};
