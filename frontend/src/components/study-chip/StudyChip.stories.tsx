import type { Story } from '@storybook/react';

import type { StudyChipProps } from '@components/study-chip/StudyChip';
import StudyChip from '@components/study-chip/StudyChip';

export default {
  title: 'Components/StudyChip',
  component: StudyChip,
  argTypes: {
    isOpen: { controls: 'boolean' },
  },
};

const Template: Story<StudyChipProps> = props => <StudyChip {...props} />;

export const Default = Template.bind({});
Default.args = {};
