import { type Story } from '@storybook/react';

import Textarea, { type TextareaProps } from '@shared/textarea/Textarea';

export default {
  title: 'Components/Textarea',
  component: Textarea,
};

const Template: Story<TextareaProps> = props => <Textarea {...props} />;

export const Default = Template.bind({});
Default.args = {
  placeholder: '인풋입니다.',
  fontSize: 'md',
  disabled: false,
  fluid: true,
  invalid: false,
};
Default.parameters = { controls: { exclude: ['onChange', 'id'] } };
