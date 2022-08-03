import type { Story } from '@storybook/react';

import { css } from '@emotion/react';

import Category from '@create-study-page/components/category/Category';
import type { CategoryProps } from '@create-study-page/components/category/Category';

export default {
  title: 'Components/Category',
  component: Category,
};

const Template: Story<CategoryProps> = props => (
  <div
    css={css`
      max-width: 400px;
    `}
  >
    <Category {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = {};
