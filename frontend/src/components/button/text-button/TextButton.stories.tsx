import type { Story } from '@storybook/react';

import { theme } from '@styles/theme';

import TextButton, { type TextButtonProps } from '@components/button/text-button/TextButton';

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
    fontSize: theme.fontSize.md,
  },
};
Default.parameters = { controls: { exclude: ['onClick'] } };
