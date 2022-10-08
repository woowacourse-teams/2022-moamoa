import { type Story } from '@storybook/react';

import BookmarkIcon from '@components/icons/bookmark-icon/BookmarkIcon';

export default {
  title: 'Materials/Icons/BookmarkIcon',
  component: BookmarkIcon,
};

const Template: Story = () => <BookmarkIcon />;

export const Default = Template.bind({});
Default.args = {};
