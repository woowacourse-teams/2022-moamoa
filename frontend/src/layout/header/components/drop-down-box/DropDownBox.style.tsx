import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { DropDownBoxProps } from '@layout/header/components/drop-down-box/DropDownBox';

export const DropDownBoxContainer = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  background: trnasparent;
  height: 100%;
  width: 100vw;
`;

export const DropDownBox = styled.div<Pick<DropDownBoxProps, 'top' | 'bottom' | 'left' | 'right'>>`
  ${({ theme, top, bottom, left, right }) => css`
    position: absolute;
    white-space: nowrap;
    ${top && `top: ${top};`}
    ${bottom && `bottom: ${bottom};`}
    ${left && `left: ${left};`}
    ${right && `right: ${right};`}
    padding: 16px;

    border: 1px solid ${theme.colors.secondary.dark};
    border-radius: 5px;
    background-color: ${theme.colors.secondary.light};
    z-index: 3;

    transform-origin: top;
    animation: slidein 0.1s ease;
    @keyframes slidein {
      0% {
        transform: scale(1, 0);
      }
      100% {
        transform: scale(1, 1);
      }
    }
  `}
`;
