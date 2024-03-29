import { type Story } from '@storybook/react';
import { useState } from 'react';

import MoreButton, { type MoreButtonProps } from '@detail-page/components/more-button/MoreButton';

export default {
  title: 'Pages/DetailPage/MoreButton',
  component: MoreButton,
  argTypes: {
    status: 'fold',
    foldText: '- 접기',
    unfoldText: '+ 더보기',
  },
};

const Template: Story<MoreButtonProps> = props => {
  const [show, setShow] = useState<boolean>(false);
  return (
    <div style={{ width: '256px' }}>
      <MoreButton {...props} status={show ? 'unfold' : 'fold'} onClick={() => setShow(prev => !prev)} />
    </div>
  );
};
export const Default = Template.bind({});
Default.args = {
  foldText: '+ 더보기',
  unfoldText: '- 접기',
};
