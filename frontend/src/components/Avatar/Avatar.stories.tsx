import { Story } from '@storybook/react';

import Avatar from '@components/Avatar';
import type { AvatarProps } from '@components/Avatar';

export default {
  title: 'Components/Avatar',
  component: Avatar,
  argTypes: {
    profileImg: { controls: 'text' },
    profileAlt: { controls: 'text' },
  },
};

const Template: Story<AvatarProps> = props => <Avatar {...props} />;

export const Default = Template.bind({});
Default.args = {
  profileImg:
    'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
  profileAlt: '프로필 이미지',
};
