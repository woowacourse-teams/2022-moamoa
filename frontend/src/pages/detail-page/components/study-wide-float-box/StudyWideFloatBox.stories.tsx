import { Story } from '@storybook/react';

import { css } from '@emotion/react';

import StudyWideFloatBox from '@detail-page/components/study-wide-float-box/StudyWideFloatBox';
import type { StudyWideFloatBoxProps } from '@detail-page/components/study-wide-float-box/StudyWideFloatBox';

export default {
  title: 'Components/StudyFloatBox',
  component: StudyWideFloatBox,
};

const Template: Story<StudyWideFloatBoxProps> = props => (
  <div
    css={css`
      max-width: 400px;
    `}
  >
    <StudyWideFloatBox {...props} />
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
