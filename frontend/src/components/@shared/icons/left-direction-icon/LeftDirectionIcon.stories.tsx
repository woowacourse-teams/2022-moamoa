import { type Story } from '@storybook/react';

import { LeftDirectionIcon } from '@shared/icons';

export default {
  title: 'Materials/Icons/LeftDirectionIcon',
  component: LeftDirectionIcon,
};

const Template: Story = () => <LeftDirectionIcon />;

export const Default = Template.bind({});
Default.args = {};
