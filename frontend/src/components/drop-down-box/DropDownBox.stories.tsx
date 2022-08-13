import type { Story } from '@storybook/react';
import { useState } from 'react';

import { css } from '@emotion/react';

import DropDownBox from '@components/drop-down-box/DropDownBox';
import type { DropDownBoxProps } from '@components/drop-down-box/DropDownBox';

export default {
  title: 'Components/DropDownBox',
  component: DropDownBox,
};

const Template: Story<DropDownBoxProps> = props => {
  const [isOpen, setIsOpen] = useState(false);

  const handleButtonClick = () => {
    setIsOpen(prev => !prev);
  };

  return (
    <div
      css={css`
        position: relative;
      `}
    >
      <button onClick={handleButtonClick}>드롭박스 열기</button>
      {isOpen && <DropDownBox {...props} onClose={() => setIsOpen(false)} />}
    </div>
  );
};

export const Default = Template.bind({});
Default.args = {
  children: 'hihi',
  top: '30px',
  left: '0',
};
Default.parameters = { controls: { exclude: ['buttonRef', 'onClose'] } };