import type { Story } from '@storybook/react';

import PlusIcon from '@design/icons/plus-icon/PlusIcon';

export default {
  title: 'Materials/Icons/PlusIcon',
  component: PlusIcon,
};

const Template: Story = () => <PlusIcon />;

export const Default = Template.bind({});
Default.args = {};
