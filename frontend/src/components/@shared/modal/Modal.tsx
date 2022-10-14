import { createPortal } from 'react-dom';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

export type ModalProps = {
  children: React.ReactNode;
  onModalOutsideClick: React.MouseEventHandler<HTMLDivElement>;
};

const Modal: React.FC<ModalProps> = ({ children, onModalOutsideClick: handleModalOutsideClick }) => {
  return (
    <ModalOutside onClick={handleModalOutsideClick}>
      <ModalContent onClick={e => e.stopPropagation()}>{children}</ModalContent>
    </ModalOutside>
  );
};

const modalElement = document.querySelector('#modal') as Element;
const ModalPortal = ({ children, onModalOutsideClick: handleModalOutsideClick }: ModalProps) => {
  return createPortal(<Modal onModalOutsideClick={handleModalOutsideClick}>{children}</Modal>, modalElement);
};

export const ModalOutside = styled.div`
  ${({ theme }) => css`
    display: flex;
    justify-content: center;
    align-items: center;

    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 10;

    width: 100vw;
    height: 100%;

    background-color: ${`${theme.colors.secondary.base}33`};
  `}
`;

// div에 바로 onClick 이벤트리스너를 달면 eslint 에러가 발생하므로 styled component를 사용함
export const ModalContent = styled.div``;

export default ModalPortal;
