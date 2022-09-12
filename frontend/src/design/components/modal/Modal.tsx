import { createPortal } from 'react-dom';

import * as S from '@design/components/modal/Modal.style';

export type ModalProps = {
  children: React.ReactNode;
  onModalOutsideClick: React.MouseEventHandler<HTMLDivElement>;
};

const Modal: React.FC<ModalProps> = ({ children, onModalOutsideClick: handleModalOutsideClick }) => {
  return (
    <S.ModalOutside onClick={handleModalOutsideClick}>
      <S.ModalContent onClick={e => e.stopPropagation()}>{children}</S.ModalContent>
    </S.ModalOutside>
  );
};

const modalElement = document.querySelector('#modal') as Element;
const ModalPortal = ({ children, onModalOutsideClick: handleModalOutsideClick }: ModalProps) => {
  return createPortal(<Modal onModalOutsideClick={handleModalOutsideClick}>{children}</Modal>, modalElement);
};

export default ModalPortal;
