import type { Story } from '@storybook/react';

import type { LetterCounterProps } from '@components/letter-counter/LetterCounter';
import LetterCounter from '@components/letter-counter/LetterCounter';

export default {
  title: 'Components/LetterCounter',
  component: LetterCounter,
  argTypes: {},
};

const Template: Story<LetterCounterProps> = props => <LetterCounter {...props} />;

export const Default = Template.bind({});
Default.args = { count: 0, maxCount: 10 };
