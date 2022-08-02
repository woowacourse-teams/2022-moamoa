import type { Story } from '@storybook/react';

import Button from '@components/button/Button';
import type { ButtonProps } from '@components/button/Button';

export default {
  title: 'Components/Button',
  component: Button,
};

const Template: Story<ButtonProps> = props => <Button {...props} />;

export const Default = Template.bind({});
Default.args = {
  children: '스터디방 가입하기',
};
