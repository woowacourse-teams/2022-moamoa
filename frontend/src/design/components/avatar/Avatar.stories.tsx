import type { Story } from '@storybook/react';

import Avatar, { type AvatarProps } from '@design/components/avatar/Avatar';

export default {
  title: 'Design/Components/Avatar',
  component: Avatar,
};

const Template: Story<AvatarProps> = props => <Avatar {...props} />;

export const Default = Template.bind({});
Default.args = {
  src: 'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
  name: '프로필 이미지',
  size: 'sm',
};
