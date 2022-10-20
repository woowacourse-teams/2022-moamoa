import { type Story } from '@storybook/react';

import { TextButton, type TextButtonProps } from '@shared/button';

export default {
  title: 'Components/TextButton',
  component: TextButton,
};

const Template: Story<TextButtonProps> = props => <TextButton {...props} />;

export const Default = Template.bind({});
Default.args = {
  children: '내 스터디',
  variant: 'primary',
  fluid: false,
  custom: {
    fontSize: 'md',
  },
};
Default.parameters = { controls: { exclude: ['onClick'] } };
