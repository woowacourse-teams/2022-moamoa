import type { Story } from '@storybook/react';

import Item, { type ItemProps } from '@design/components/item/Item';

export default {
  title: 'Design/Components/Item',
  component: Item,
};

const Template: Story<ItemProps> = props => (
  <Item {...props}>
    <Item.Heading>제목</Item.Heading>
    <Item.Content>내용</Item.Content>
  </Item>
);

export const Default = Template.bind({});
Default.args = {
  src: 'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
  name: 'person',
  size: 'lg',
};
