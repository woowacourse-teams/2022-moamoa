import { useEffect } from 'react';

import type { Noop } from '@custom-types';

import * as S from '@components/drop-down-box/DropDownBox.style';

export type DropDownBoxProps = {
  className?: string;
  children: React.ReactNode;
  onClose: Noop;
  top?: string;
  bottom?: string;
  left?: string;
  right?: string;
};

const DropDownBox: React.FC<DropDownBoxProps> = ({ className, children, onClose: handleClose, ...positions }) => {
  useEffect(() => {
    // 이벤트 전파가 끝나기 전에 document에 click event listener가 붙기 때문에
    // click event listener를 add하는 일을 다음 frame으로 늦춘다
    // Test: https://codepen.io/airman5573/pen/qBopRpO
    requestAnimationFrame(() => document.body.addEventListener('click', handleClose));
    return () => document.body.removeEventListener('click', handleClose);
  }, []);

  return (
    <S.DropDownBox className={className} {...positions}>
      {children}
    </S.DropDownBox>
  );
};

export default DropDownBox;
