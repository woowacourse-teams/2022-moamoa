import { type Story } from '@storybook/react';

import MultiTagSelect, { type MultiTagSelectProps } from '@shared/multi-tag-select/MultiTagSelect';

export default {
  title: 'Components/MultiTagSelect',
  component: MultiTagSelect,
  argTypes: {},
};

const Template: Story<MultiTagSelectProps> = props => <MultiTagSelect {...props} />;

export const Default = Template.bind({});
Default.args = {
  options: [
    {
      value: 1,
      label: 'apple',
    },
    {
      value: 2,
      label: 'orange',
    },
    {
      value: 3,
      label: 'banana',
    },
    {
      value: 4,
      label: 'tomato',
    },
    {
      value: 5,
      label: 'bread',
    },
  ],
};
