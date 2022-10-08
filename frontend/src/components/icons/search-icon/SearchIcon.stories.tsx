import { type Story } from '@storybook/react';

import SearchIcon from '@components/icons/search-icon/SearchIcon';

export default {
  title: 'Materials/Icons/SearchIcon',
  component: SearchIcon,
};

const Template: Story = () => <SearchIcon />;

export const Default = Template.bind({});
Default.args = {};
