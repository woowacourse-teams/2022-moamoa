import { type Story } from '@storybook/react';

import PencilIcon from '@components/icons/pencil-icon/PencilIcon';

export default {
  title: 'Materials/Icons/PencilIcon',
  component: PencilIcon,
};

const Template: Story = () => <PencilIcon />;

export const Default = Template.bind({});
Default.args = {};
