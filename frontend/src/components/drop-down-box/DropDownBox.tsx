import { useEffect, useRef } from 'react';

import type { CssLength, Noop } from '@custom-types';

import * as S from '@components/drop-down-box/DropDownBox.style';

export type DropDownBoxProps = {
  children: React.ReactNode;
  isOpen: boolean;
  onClose: Noop;
  top?: CssLength;
  bottom?: CssLength;
  left?: CssLength;
  right?: CssLength;
  padding?: CssLength;
};

const DropDownBox: React.FC<DropDownBoxProps> = ({ children, isOpen, onClose, padding, ...positions }) => {
  const ref = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (!isOpen || !ref.current) return;

    const handleClose = (e: MouseEvent) => {
      if (e.target === null) return;

      const path = e.composedPath();
      const isDropDownBoxClicked = path.some(el => el === ref.current);

      !isDropDownBoxClicked && onClose();
    };

    // 이벤트 전파가 끝나기 전에 document에 click event listener가 붙기 때문에
    // click event listener를 add하는 일을 다음 frame으로 늦춥니다
    // Test: https://codepen.io/airman5573/pen/qBopRpO
    requestAnimationFrame(() => document.body.addEventListener('click', handleClose));
    return () => document.body.removeEventListener('click', handleClose);
  }, [isOpen, onClose]);

  if (!isOpen) return <></>;

  return (
    <S.DropDownBox {...positions} padding={padding} ref={ref}>
      {children}
    </S.DropDownBox>
  );
};

export default DropDownBox;
