import { type Story } from '@storybook/react';

import LetterCounter, { type LetterCounterProps } from '@shared/letter-counter/LetterCounter';

export default {
  title: 'Components/LetterCounter',
  component: LetterCounter,
  argTypes: {},
};

const Template: Story<LetterCounterProps> = props => <LetterCounter {...props} />;

export const Default = Template.bind({});
Default.args = { count: 0, maxCount: 10 };
