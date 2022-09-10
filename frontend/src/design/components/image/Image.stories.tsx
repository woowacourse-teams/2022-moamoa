import type { Story } from '@storybook/react';

import Image, { type ImageProps } from '@design/components/image/Image';

export default {
  title: 'Design/Components/Image',
  component: Image,
};

const Template: Story<ImageProps> = props => <Image {...props} />;

export const Default = Template.bind({});
Default.args = {
  shape: 'rectangular',
  src: 'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
  alt: '프로필 이미지',
  width: '5rem',
  height: '5rem',
};
