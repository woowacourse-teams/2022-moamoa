import { type Story } from '@storybook/react';

import { TrashcanIcon } from '@shared/icons';

export default {
  title: 'Materials/Icons/TrashcanIcon',
  component: TrashcanIcon,
};

const Template: Story = () => <TrashcanIcon />;

export const Default = Template.bind({});
Default.args = {};
