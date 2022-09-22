import { useEffect, useRef } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { CssLength, Noop } from '@custom-types';

export type DropDownBoxProps = {
  children: React.ReactNode;
  className?: string;
  isOpen: boolean;
  onClose: Noop;
  top?: CssLength;
  bottom?: CssLength;
  left?: CssLength;
  right?: CssLength;
  padding?: CssLength;
};

const DropDownBox: React.FC<DropDownBoxProps> = ({ className, children, isOpen, onClose, padding, ...positions }) => {
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

  return (
    <>
      {isOpen && (
        <Self className={className} {...positions} padding={padding} ref={ref}>
          {children}
        </Self>
      )}
    </>
  );
};

type StyledDropDownBox = Pick<DropDownBoxProps, 'top' | 'bottom' | 'left' | 'right' | 'padding'>;

export const Self = styled.div<StyledDropDownBox>`
  ${({ theme, top, bottom, left, right, padding }) => css`
    position: absolute;
    ${(top || top === 0) && `top: ${top};`}
    ${(bottom || bottom === 0) && `bottom: ${bottom};`}
    ${(left || left === 0) && `left: ${left};`}
    ${(right || right === 0) && `right: ${right};`}
    z-index: 3;
    white-space: nowrap;

    ${padding && `padding: ${padding};`}

    border: 1px solid ${theme.colors.secondary.dark};
    border-radius: ${theme.radius.xs};
    background-color: ${theme.colors.secondary.light};

    transform-origin: top;
    animation: slide-down 0.1s ease;
    overflow-y: auto;
    @keyframes slide-down {
      0% {
        transform: scale(1, 0);
      }
      100% {
        transform: scale(1, 1);
      }
    }
  `}
`;

export default DropDownBox;
