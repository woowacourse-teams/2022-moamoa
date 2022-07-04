import { Story } from '@storybook/react';

import type { CardProps } from '@components/Card';
import Card from '@components/Card';

export default {
  title: 'Components/Card',
  component: Card,
  argTypes: {
    thumbnailUrl: { controls: 'text' },
    thumbnailAlt: { controls: 'text' },
    title: { controls: 'text' },
    description: { controls: 'text' },
    chipText: { controls: 'text' },
    chipDisabled: { controls: 'boolean' },
  },
};

const Template: Story<CardProps> = props => <Card {...props} />;

export const Default = Template.bind({});
Default.args = {
  thumbnailUrl:
    'https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1673&q=80',
  thumbnailAlt: '자바스크립트 스터디 이미지',
  title: '자바스크립트 스터디',
  description: '자바스크립트 스터디',
  chipText: '모집완료',
  chipDisabled: true,
};
