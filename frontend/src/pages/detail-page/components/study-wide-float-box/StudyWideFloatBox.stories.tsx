import type { Story } from '@storybook/react';

import { noop } from '@utils';

import StudyWideFloatBox from '@detail-page/components/study-wide-float-box/StudyWideFloatBox';
import type { StudyWideFloatBoxProps } from '@detail-page/components/study-wide-float-box/StudyWideFloatBox';

export default {
  title: 'Pages/DetailPage/StudyWideFloatBox',
  component: StudyWideFloatBox,
};

const Template: Story<StudyWideFloatBoxProps> = props => (
  <div
    style={{
      width: '700px',
    }}
  >
    <StudyWideFloatBox {...props} onRegisterButtonClick={() => noop} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  enrollmentEndDate: '2022-07-28',
  currentMemberCount: 8,
  maxMemberCount: 14,
};
