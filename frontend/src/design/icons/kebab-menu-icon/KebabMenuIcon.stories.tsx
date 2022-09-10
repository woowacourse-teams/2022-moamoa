import type { Story } from '@storybook/react';

import KebabMenuIcon from '@design/icons/kebab-menu-icon/KebabMenuIcon';

export default {
  title: 'Materials/Icons/KebabMenuIcon',
  component: KebabMenuIcon,
};

const Template: Story = () => <KebabMenuIcon />;

export const Default = Template.bind({});
Default.args = {};
