import { type Story } from '@storybook/react';

import IconButton, { type IconButtonProps } from '@shared/button/icon-button/IconButton';
import { PlusIcon } from '@shared/icons';

export default {
  title: 'Components/IconButton',
  component: IconButton,
};

const Template: Story<IconButtonProps> = props => <IconButton {...props} />;

export const Default = Template.bind({});
Default.args = {
  children: <PlusIcon />,
  ariaLabel: '더하기',
  variant: 'primary',
  custom: {
    width: '30px',
    height: '30px',
  },
};
Default.parameters = { controls: { exclude: ['children', 'onClick'] } };
