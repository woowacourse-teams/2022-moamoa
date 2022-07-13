import { Story } from '@storybook/react';

import type { CheckListItemProps } from './CheckListItem';
import CheckListItem from './CheckListItem';

export default {
  title: 'Components/CheckListItem',
  component: CheckListItem,
  argTypes: {
    children: { controls: 'text' },
    isChecked: { controls: 'boolean' },
  },
};

const Template: Story<CheckListItemProps> = props => (
  <div style={{ width: '200px' }}>
    <CheckListItem {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  children: '필터',
  isChecked: false,
};

export const Checked = Template.bind({});
Checked.args = {
  children: '필터',
  isChecked: true,
};
Checked.parameters = { controls: { exclude: ['isChecked'] } };

export const Unchecked = Template.bind({});
Unchecked.args = {
  children: '필터',
  isChecked: false,
};
Unchecked.parameters = { controls: { exclude: ['isChecked'] } };
