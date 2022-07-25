import { Story } from '@storybook/react';

import TagChip from '@components/tag-chip/TagChip';
import type { TagChipProps } from '@components/tag-chip/TagChip';

export default {
  title: 'Components/TagChip',
  component: TagChip,
  argTypes: {
    children: { controls: 'text' },
  },
};

const Template: Story<TagChipProps> = props => <TagChip {...props} />;

export const Default = Template.bind({});
Default.args = {
  children: '#4기',
};
