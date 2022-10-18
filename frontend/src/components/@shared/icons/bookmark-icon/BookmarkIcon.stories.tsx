import { type Story } from '@storybook/react';

import { BookmarkIcon } from '@shared/icons';

export default {
  title: 'Materials/Icons/BookmarkIcon',
  component: BookmarkIcon,
};

const Template: Story = () => <BookmarkIcon />;

export const Default = Template.bind({});
Default.args = {};
