import type { Story } from '@storybook/react';

import MetaBox from '@design/components/meta-box/MetaBox';

export default {
  title: 'Design/Components/MetaBox',
  component: MetaBox,
};

const Template: Story = () => (
  <MetaBox>
    <MetaBox.Title>스터디 인원</MetaBox.Title>
    <MetaBox.Content>Content입니다</MetaBox.Content>
  </MetaBox>
);

export const Default = Template.bind({});
Default.args = {};
