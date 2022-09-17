import type { Story } from '@storybook/react';
import { useState } from 'react';

import { noop } from '@utils';

import { BoxButton } from '@components/button';
import Modal from '@components/modal/Modal';
import type { ModalProps } from '@components/modal/Modal';

import LinkForm from '@study-room-page/tabs/link-room-tab-panel/components/link-form/LinkForm';

export default {
  title: 'Components/Modal',
  component: Modal,
};

const Template: Story<ModalProps> = () => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <>
      <BoxButton type="button" onClick={() => setIsOpen(prev => !prev)} fluid={false}>
        모달 열기
      </BoxButton>
      {isOpen && (
        <Modal onModalOutsideClick={() => setIsOpen(false)}>
          <LinkForm
            author={{
              id: 20,
              username: 'tco0427',
              imageUrl:
                'https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80',
              profileUrl: 'github.com',
            }}
            onPostError={noop}
            onPostSuccess={noop}
          />
        </Modal>
      )}
    </>
  );
};

export const Default = Template.bind({});
Default.args = {};
