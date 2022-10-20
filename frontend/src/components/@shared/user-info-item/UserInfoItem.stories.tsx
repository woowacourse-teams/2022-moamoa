import { type Story } from '@storybook/react';

import UserInfoItem, { type UserInfoItemProps } from '@shared/user-info-item/UserInfoItem';

export default {
  title: 'Components/UserInfoItem',
  component: UserInfoItem,
};

const Template: Story<UserInfoItemProps> = props => (
  <UserInfoItem {...props}>
    <UserInfoItem.Heading>제목</UserInfoItem.Heading>
    <UserInfoItem.Content>내용</UserInfoItem.Content>
  </UserInfoItem>
);

export const Default = Template.bind({});
Default.args = {
  src: 'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
  name: 'person',
  size: 'lg',
};
