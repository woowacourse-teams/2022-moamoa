import type { Story } from '@storybook/react';

import tw from '@utils/tw';

import MetaBox from '@create-study-page/components/meta-box/MetaBox';

export default {
  title: 'Components/MetaBox',
  component: MetaBox,
};

const Template: Story = () => (
  <MetaBox css={tw`max-w-[300px]`}>
    <MetaBox.Title>스터디 인원</MetaBox.Title>
    <MetaBox.Content>Content입니다</MetaBox.Content>
  </MetaBox>
);

export const Default = Template.bind({});
Default.args = {};
