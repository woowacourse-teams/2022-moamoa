import { Story } from '@storybook/react';

import SearchBar from '@layout/header/components/search-bar/SearchBar';
import type { SearchBarProps } from '@layout/header/components/search-bar/SearchBar';

export default {
  title: 'Components/SearchBar',
  component: SearchBar,
};

const Template: Story<SearchBarProps> = props => <SearchBar {...props} />;

export const Default = Template.bind({});
Default.args = {};
