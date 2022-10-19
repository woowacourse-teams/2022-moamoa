import { type Story } from '@storybook/react';

import { noop } from '@utils';

import StudyFloatBox, { type StudyFloatBoxProps } from '@detail-page/components/study-float-box/StudyFloatBox';

export default {
  title: 'Pages/DetailPage/StudyFloatBox',
  component: StudyFloatBox,
};

const Template: Story<StudyFloatBoxProps> = props => (
  <div style={{ width: '400px' }}>
    <StudyFloatBox {...props} onRegisterButtonClick={() => noop} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  enrollmentEndDate: '2022-07-28',
  currentMemberCount: 8,
  maxMemberCount: 14,
  ownerName: 'airman5573',
};
