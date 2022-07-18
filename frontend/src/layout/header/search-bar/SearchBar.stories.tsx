import { Story } from '@storybook/react';

import SearchBar from '@layout/header/search-bar/SearchBar';
import type { SearchBarProps } from '@layout/header/search-bar/SearchBar';

export default {
  title: 'Components/SearchBar',
  component: SearchBar,
};

const Template: Story<SearchBarProps> = props => <SearchBar {...props} />;

export const Default = Template.bind({});
Default.args = {};
