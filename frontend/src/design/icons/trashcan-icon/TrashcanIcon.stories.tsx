import type { Story } from '@storybook/react';

import TrashcanIcon from '@design/icons/trashcan-icon/TrashcanIcon';

export default {
  title: 'Materials/Icons/TrashcanIcon',
  component: TrashcanIcon,
};

const Template: Story = () => <TrashcanIcon />;

export const Default = Template.bind({});
Default.args = {};
