import { type Story } from '@storybook/react';

import Flex, { type FlexBoxProps } from '@shared/flex/Flex';

export default {
  title: 'Components/Flex',
  component: Flex,
};

const Template: Story<FlexBoxProps> = props => (
  <Flex {...props}>
    <div>div1</div>
    <div>div2</div>
    <div>div3</div>
    <div>div4</div>
    <Flex.Item>flex-item</Flex.Item>
  </Flex>
);

export const Default = Template.bind({});
Default.args = {};
