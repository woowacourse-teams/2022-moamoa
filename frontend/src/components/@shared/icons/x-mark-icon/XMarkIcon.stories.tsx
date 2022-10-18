import { type Story } from '@storybook/react';

import XMarkIcon, { type XMarkIconProps } from '@shared/icons/x-mark-icon/XMarkIcon';

export default {
  title: 'Materials/Icons/XMarkIcon',
  component: XMarkIcon,
  argTypes: {
    size: {
      options: ['sm', 'base'],
      control: { type: 'radio' },
    },
  },
};

const Template: Story<XMarkIconProps> = ({ size }) => <XMarkIcon size={size} />;

export const Default = Template.bind({});
Default.args = {};
