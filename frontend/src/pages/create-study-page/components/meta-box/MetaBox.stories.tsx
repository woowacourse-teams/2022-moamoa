import type { Story } from '@storybook/react';

import { css } from '@emotion/react';

import MetaBox from '@create-study-page/components/meta-box/MetaBox';

export default {
  title: 'Components/MetaBox',
  component: MetaBox,
};

const Template: Story = () => (
  <MetaBox
    css={css`
      max-width: 300px;
    `}
  >
    <MetaBox.Title>스터디 인원</MetaBox.Title>
    <MetaBox.Content>Content입니다</MetaBox.Content>
  </MetaBox>
);

export const Default = Template.bind({});
Default.args = {};
