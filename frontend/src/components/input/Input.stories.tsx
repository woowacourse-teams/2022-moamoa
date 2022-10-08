import { type Story } from '@storybook/react';

import Input, { type InputProps } from '@components/input/Input';

export default {
  title: 'Components/Input',
  component: Input,
};

const Template: Story<InputProps> = props => <Input {...props} />;

export const Default = Template.bind({});
Default.args = {
  placeholder: '인풋입니다.',
  fontSize: 'md',
  disabled: false,
  invalid: false,
  fluid: false,
};
Default.parameters = { controls: { exclude: ['onChange', 'id'] } };
