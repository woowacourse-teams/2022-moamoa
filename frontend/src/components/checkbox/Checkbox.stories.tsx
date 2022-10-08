import { type Story } from '@storybook/react';

import Checkbox, { type CheckboxProps } from '@components/checkbox/Checkbox';

export default {
  title: 'Components/Checkbox',
  component: Checkbox,
};

const Template: Story<CheckboxProps> = props => <Checkbox {...props} />;

export const Default = Template.bind({});
Default.args = {
  children: '주제',
  checked: true,
  dataTagId: 1,
};
Default.parameters = { controls: { exclude: ['dataTagId', 'onChange'] } };
