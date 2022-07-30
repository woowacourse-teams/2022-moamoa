import { Story } from '@storybook/react';

import NavButton from '@layout/header/components/nav-button/NavButton';
import type { NavButtonProps } from '@layout/header/components/nav-button/NavButton';

export default {
  title: 'Components/NavButton',
  component: NavButton,
  argTypes: {
    children: { controls: 'text' },
  },
};

const Template: Story<NavButtonProps> = props => <NavButton {...props} />;

export const Default = Template.bind({});
Default.args = {
  children: '➕ hihi',
};
