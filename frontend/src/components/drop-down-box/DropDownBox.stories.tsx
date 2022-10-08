import { type Story } from '@storybook/react';
import { useState } from 'react';

import tw from '@utils/tw';

import { BoxButton } from '@components/button';
import DropDownBox, { type DropDownBoxProps } from '@components/drop-down-box/DropDownBox';

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
    <div css={tw`w-200 h-200`}>
      <div css={tw`relative`}>
        <BoxButton type="button" onClick={handleButtonClick}>
          드롭박스 열기
        </BoxButton>
        {isOpen && <DropDownBox {...props} onClose={() => setIsOpen(false)} />}
      </div>
    </div>
  );
};

export const Default = Template.bind({});
Default.args = {
  children: 'hihi',
  top: '70px',
  right: 0,
  custom: {
    padding: '16px',
  },
};
Default.parameters = { controls: { exclude: ['buttonRef', 'onClose'] } };
