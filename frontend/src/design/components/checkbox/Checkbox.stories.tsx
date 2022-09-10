import type { Story } from '@storybook/react';

import Checkbox, { type CheckboxProps } from '@design/components/checkbox/Checkbox';

export default {
  title: 'Design/Components/Checkbox',
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
