import type { Story } from '@storybook/react';
import { useState } from 'react';

import { css } from '@emotion/react';

import Modal from '@components/modal/Modal';
import type { ModalProps } from '@components/modal/Modal';

export default {
  title: 'Components/Modal',
  component: Modal,
};

const Template: Story<ModalProps> = () => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <>
      <button type="button" onClick={() => setIsOpen(prev => !prev)}>
        모달 열기
      </button>
      {isOpen && (
        <Modal onModalOutsideClick={() => setIsOpen(false)}>
          <div
            css={css`
              background-color: black;
              padding: 20px;
            `}
          >
            <button type="button" onClick={() => alert('클릭!')}>
              alert창이 뜹니다
            </button>
          </div>
        </Modal>
      )}
    </>
  );
};

export const Default = Template.bind({});
Default.args = {};
