import { Story } from '@storybook/react';

import SearchBar from '@components/SearchBar';

export default {
  title: 'Components/SearchBar',
  component: SearchBar,
};

const Template: Story = props => <SearchBar {...props} />;

export const Default = Template.bind({});
Default.args = {};
