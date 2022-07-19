import { Story } from '@storybook/react';

import { css } from '@emotion/react';

import StudyFloatBox from '@detail-page/components/study-float-box/StudyFloatBox';
import type { StudyFloatBoxProps } from '@detail-page/components/study-float-box/StudyFloatBox';

export default {
  title: 'Components/StudyFloatBox',
  component: StudyFloatBox,
};

const Template: Story<StudyFloatBoxProps> = props => (
  <div
    css={css`
      max-width: 400px;
    `}
  >
    <StudyFloatBox {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  studyId: '_3f41',
  deadline: '2022-07-28',
  currentMemberCount: 8,
  maxMemberCount: 14,
  owner: 'airman5573',
};
