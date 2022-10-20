import { type Story } from '@storybook/react';

import { FolderIcon } from '@shared/icons';

export default {
  title: 'Materials/Icons/FolderIcon',
  component: FolderIcon,
};

const Template: Story = () => <FolderIcon />;

export const Default = Template.bind({});
Default.args = {};
