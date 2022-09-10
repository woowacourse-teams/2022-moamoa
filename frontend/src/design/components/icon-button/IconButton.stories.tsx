import type { Story } from '@storybook/react';

import IconButton, { type IconButtonProps } from '@design/components/icon-button/IconButton';
import PlusIcon from '@design/icons/plus-icon/PlusIcon';

export default {
  title: 'Design/Components/IconButton',
  component: IconButton,
};

const Template: Story<IconButtonProps> = props => <IconButton {...props} />;

export const Default = Template.bind({});
Default.args = {
  children: <PlusIcon />,
  ariaLabel: '더하기',
  variant: 'primary',
  width: '30px',
  height: '30px',
};
Default.parameters = { controls: { exclude: ['children', 'onClick'] } };
