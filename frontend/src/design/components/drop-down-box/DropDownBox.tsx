import { useEffect } from 'react';

import type { CssLength, Noop } from '@custom-types';

import * as S from '@design/components/drop-down-box/DropDownBox.style';

export type DropDownBoxProps = {
  children: React.ReactNode;
  onClose: Noop;
  top?: CssLength;
  bottom?: CssLength;
  left?: CssLength;
  right?: CssLength;
  padding?: CssLength;
};

const DropDownBox: React.FC<DropDownBoxProps> = ({ children, onClose: handleClose, padding, ...positions }) => {
  useEffect(() => {
    // 이벤트 전파가 끝나기 전에 document에 click event listener가 붙기 때문에
    // click event listener를 add하는 일을 다음 frame으로 늦춘다
    // Test: https://codepen.io/airman5573/pen/qBopRpO
    requestAnimationFrame(() => document.body.addEventListener('click', handleClose));
    return () => document.body.removeEventListener('click', handleClose);
  }, []);

  return (
    <S.DropDownBox {...positions} padding={padding}>
      {children}
    </S.DropDownBox>
  );
};

export default DropDownBox;
