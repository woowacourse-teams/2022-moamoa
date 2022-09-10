import type { Story } from '@storybook/react';

import TextButton, { type TextButtonProps } from '@design/components/text-button/TextButton';

export default {
  title: 'Design/Components/TextButton',
  component: TextButton,
};

const Template: Story<TextButtonProps> = props => <TextButton {...props} />;

export const Default = Template.bind({});
Default.args = {
  children: '내 스터디',
  fontSize: 'md',
  variant: 'primary',
  fluid: false,
};
Default.parameters = { controls: { exclude: ['onClick'] } };
