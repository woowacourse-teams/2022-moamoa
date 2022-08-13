import { useEffect } from 'react';

import * as S from '@components/drop-down-box/DropDownBox.style';

export type DropDownBoxProps = {
  children: React.ReactNode;
  buttonRef: React.MutableRefObject<HTMLButtonElement | null>;
  onClose: () => void;
  top?: string;
  bottom?: string;
  left?: string;
  right?: string;
};

const DropDownBox: React.FC<DropDownBoxProps> = ({ children, buttonRef, onClose: handleClose, ...positions }) => {
  const handleDropDownBoxClose = (e: MouseEvent) => {
    if (e.target === buttonRef.current) {
      return;
    }
    handleClose();
  };

  useEffect(() => {
    document.body.addEventListener('click', handleDropDownBoxClose);
    return () => document.body.removeEventListener('click', handleDropDownBoxClose);
  }, []);

  return <S.DropDownBox {...positions}>{children}</S.DropDownBox>;
};

export default DropDownBox;
