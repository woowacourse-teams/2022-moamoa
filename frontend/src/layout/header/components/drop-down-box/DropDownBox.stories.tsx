import { Story } from '@storybook/react';
import { useState } from 'react';

import DropDownBox from '@layout/header/components/drop-down-box/DropDownBox';
import type { DropDownBoxProps } from '@layout/header/components/drop-down-box/DropDownBox';

export default {
  title: 'Components/DropDownBox',
  component: DropDownBox,
  argTypes: {
    children: { controls: 'text' },
    top: { controls: 'text' },
    right: { controls: 'text' },
    left: { controls: 'text' },
    bottom: { controls: 'text' },
  },
};

const Template: Story<DropDownBoxProps> = props => {
  const [isOpen, setIsOpen] = useState(false);
  return (
    <div>
      <button onClick={() => setIsOpen(prev => !prev)}>드롭박스 열기</button>
      {isOpen && <DropDownBox {...props} onOutOfBoxClick={() => setIsOpen(false)} />}
    </div>
  );
};

export const Default = Template.bind({});
Default.args = {
  children: 'hihi',
  top: '40px',
  left: '15px',
};
