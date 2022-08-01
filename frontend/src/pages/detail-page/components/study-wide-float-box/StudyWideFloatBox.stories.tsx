import { Story } from '@storybook/react';

import { noop } from '@utils/index';

import StudyWideFloatBox from '@detail-page/components/study-wide-float-box/StudyWideFloatBox';
import type { StudyWideFloatBoxProps } from '@detail-page/components/study-wide-float-box/StudyWideFloatBox';

export default {
  title: 'Components/StudyWideFloatBox',
  component: StudyWideFloatBox,
};

const Template: Story<StudyWideFloatBoxProps> = props => (
  <div
    style={{
      width: '700px',
    }}
  >
    <StudyWideFloatBox {...props} handleRegisterButtonClick={() => noop} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  studyId: 123,
  enrollmentEndDate: '2022-07-28',
  currentMemberCount: 8,
  maxMemberCount: 14,
};
