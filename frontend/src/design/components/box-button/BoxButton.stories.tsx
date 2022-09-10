import type { Story } from '@storybook/react';

import BoxButton, { type BoxButtonProps } from '@design/components/box-button/BoxButton';

export default {
  title: 'Design/Components/BoxButton',
  component: BoxButton,
};

const Template: Story<BoxButtonProps> = props => <BoxButton {...props} />;

export const Default = Template.bind({});
Default.args = {
  variant: 'primary',
  children: '스터디방 가입하기',
  fluid: false,
  disabled: false,
};
Default.parameters = { controls: { exclude: ['onClick', 'type'] } };
