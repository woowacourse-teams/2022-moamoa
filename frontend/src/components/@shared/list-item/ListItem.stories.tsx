import { type Story } from '@storybook/react';

import ListItem, { type ListItemProps } from '@shared/list-item/ListItem';

export default {
  title: 'Components/ListItem',
  component: ListItem,
  argTypes: {
    isOpen: { controls: 'boolean' },
  },
};

const Template: Story<ListItemProps> = props => <ListItem {...props} />;

export const Default = Template.bind({});
Default.args = {
  title: '게시글 제목입니다.',
  userInfo: {
    src: 'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
    username: 'person',
  },
  subInfo: '2022-10-10',
};
