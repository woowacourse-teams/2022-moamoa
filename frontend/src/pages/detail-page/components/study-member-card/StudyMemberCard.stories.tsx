import { Story } from '@storybook/react';

import StudyMemberCard from '@detail-page/components/study-member-card/StudyMemberCard';
import type { StudyMemberCardProp } from '@detail-page/components/study-member-card/StudyMemberCard';

export default {
  title: 'Components/StudyMemberCard',
  component: StudyMemberCard,
};

const Template: Story<StudyMemberCardProp> = props => (
  <div style={{ maxWidth: '350px', minWidth: 'fit-content' }}>
    <StudyMemberCard {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  imageUrl: 'https://picsum.photos/id/186/200/200',
  username: 'airman5573',
};
