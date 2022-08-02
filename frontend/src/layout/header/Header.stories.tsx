import { Story } from '@storybook/react';

import Header from '@layout/header/Header';
import type { HeaderProps } from '@layout/header/Header';

export default {
  title: 'Components/Header',
  component: Header,
};

const Template: Story<HeaderProps> = props => <Header {...props} />;

export const Default = Template.bind({});
Default.args = {};
