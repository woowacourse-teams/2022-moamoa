import Category from '@create-study-page/components/category/Category';
import { Story } from '@storybook/react';

import { css } from '@emotion/react';

export default {
  title: 'Components/Category',
  component: Category,
};

const Template: Story = props => (
  <div
    css={css`
      max-width: 400px;
    `}
  >
    <Category />
  </div>
);

export const Default = Template.bind({});
Default.args = {};
