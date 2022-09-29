import type { Story } from '@storybook/react';

import LeftDirectionIcon from '@components/icons/left-direction-icon/LeftDirectionIcon';

export default {
  title: 'Materials/Icons/LeftDirectionIcon',
  component: LeftDirectionIcon,
};

const Template: Story = () => <LeftDirectionIcon />;

export const Default = Template.bind({});
Default.args = {};
