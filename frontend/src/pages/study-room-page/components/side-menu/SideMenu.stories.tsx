import { Story } from '@storybook/react';

import SideMenu from '@study-room-page/components/side-menu/SideMenu';

export default {
  title: 'Components/SideMenu',
  component: SideMenu,
  argTypes: {
    children: { controls: 'text' },
    isSelected: { controls: 'boolean' },
  },
};

const Template: Story = props => <SideMenu {...props} />;

export const Default = Template.bind({});
Default.args = {
  children: '공지사항',
  isSelected: false,
};
Default.parameters = { controls: { exclude: ['isSelected', 'onClick'] } };
