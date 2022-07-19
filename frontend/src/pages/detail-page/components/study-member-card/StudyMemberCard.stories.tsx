import { Story } from '@storybook/react';

import { css } from '@emotion/react';

import StudyMemberCard from '@detail-page/components/study-member-card/StudyMemberCard';
import type { StudyMemberCardProp } from '@detail-page/components/study-member-card/StudyMemberCard';

export default {
  title: 'Components/StudyMemberCard',
  component: StudyMemberCard,
};

const Template: Story<StudyMemberCardProp> = props => (
  <StudyMemberCard
    css={css`
      max-width: 300px;
    `}
    {...props}
  />
);

export const Default = Template.bind({});
Default.args = {
  profileImage: 'https://picsum.photos/id/186/200/200',
  username: 'airman5573',
};
