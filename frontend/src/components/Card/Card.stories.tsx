import { Story } from '@storybook/react';

import type { CardProps } from '@components/Card';
import Card from '@components/Card';
import Chip from '@components/Chip';

export default {
  title: 'Components/Card',
  component: Card,
  argTypes: {
    thumbnailUrl: { controls: 'text' },
    thumbnailAlt: { controls: 'text' },
    title: { controls: 'text' },
    description: { controls: 'text' },
    extraChips: { controls: 'object' },
  },
};

const Template: Story<CardProps> = props => (
  <div style={{ width: '256px' }}>
    <Card {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  thumbnailUrl:
    'https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1673&q=80',
  thumbnailAlt: '이미지 Alt',
  title: '타이틀',
  description: '세부 설명',
  extraChips: [<Chip disabled={false}>Chip</Chip>],
};
