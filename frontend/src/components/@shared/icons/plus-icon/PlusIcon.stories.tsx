import { type Story } from '@storybook/react';

import { PlusIcon } from '@shared/icons';

export default {
  title: 'Materials/Icons/PlusIcon',
  component: PlusIcon,
};

const Template: Story = () => <PlusIcon />;

export const Default = Template.bind({});
Default.args = {};
