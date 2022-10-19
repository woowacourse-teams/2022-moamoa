import { type Story } from '@storybook/react';

import NavButton, { type NavButtonProps } from '@layout/header/components/nav-button/NavButton';

export default {
  title: 'Layout/NavButton',
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
