import type { Story } from '@storybook/react';

import tw from '@utils/tw';

import Divider, { type DividerProps } from '@design/components/divider/Divider';

export default {
  title: 'Design/Components/Divider',
  component: Divider,
};

const Template: Story<DividerProps> = props => (
  <div css={tw`h-100 w-100`}>
    <Divider {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  orientation: 'vertical',
  color: '#000051',
  space: '10px',
};
