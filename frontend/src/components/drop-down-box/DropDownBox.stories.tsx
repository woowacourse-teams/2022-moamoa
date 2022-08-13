import type { Story } from '@storybook/react';
import { useRef, useState } from 'react';

import { css } from '@emotion/react';

import DropDownBox from '@components/drop-down-box/DropDownBox';
import type { DropDownBoxProps } from '@components/drop-down-box/DropDownBox';

export default {
  title: 'Components/DropDownBox2',
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
  const buttonRef = useRef<HTMLButtonElement | null>(null);
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
      <button ref={buttonRef} onClick={handleButtonClick}>
        드롭박스 열기
      </button>
      {isOpen && <DropDownBox {...props} buttonRef={buttonRef} onClose={() => setIsOpen(false)} />}
    </div>
  );
};

export const Default = Template.bind({});
Default.args = {
  children: 'hihi',
  top: '30px',
  left: '0',
};
