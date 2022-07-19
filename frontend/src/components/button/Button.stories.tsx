import { Story } from '@storybook/react';

import Button from '@components/button/Button';
import type { ButtonProp } from '@components/button/Button';

export default {
  title: 'Components/Button',
  component: Button,
};

const Template: Story<ButtonProp> = props => <Button {...props} />;

export const Default = Template.bind({});
Default.args = {
  children: '스터디방 가입하기',
};
