import { type Story } from '@storybook/react';

import { MeatballMenuIcon } from '@shared/icons';

export default {
  title: 'Materials/Icons/MeatballMenuIcon',
  component: MeatballMenuIcon,
};

const Template: Story = () => <MeatballMenuIcon />;

export const Default = Template.bind({});
Default.args = {};
