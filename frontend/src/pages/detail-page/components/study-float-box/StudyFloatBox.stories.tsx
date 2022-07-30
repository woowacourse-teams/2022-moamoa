import { Story } from '@storybook/react';

import { noop } from '@utils/index';

import StudyFloatBox from '@detail-page/components/study-float-box/StudyFloatBox';
import type { StudyFloatBoxProps } from '@detail-page/components/study-float-box/StudyFloatBox';

export default {
  title: 'Components/StudyFloatBox',
  component: StudyFloatBox,
};

const Template: Story<StudyFloatBoxProps> = props => (
  <div style={{ width: '400px' }}>
    <StudyFloatBox {...props} handleRegisterBtnClick={() => noop} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  studyId: 232,
  enrollmentEndDate: '2022-07-28',
  currentMemberCount: 8,
  maxMemberCount: 14,
  ownerName: 'airman5573',
};
