import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { DropDownBoxProps } from '@components/drop-down-box/DropDownBox';

export const DropDownBox = styled.div<Pick<DropDownBoxProps, 'top' | 'bottom' | 'left' | 'right'>>`
  ${({ theme, top, bottom, left, right }) => css`
    position: absolute;
    ${top && `top: ${top};`}
    ${bottom && `bottom: ${bottom};`}
    ${left && `left: ${left};`}
    ${right && `right: ${right};`}
    z-index: 3;
    white-space: nowrap;

    border: 1px solid ${theme.colors.secondary.dark};
    border-radius: ${theme.radius.xs};
    background-color: ${theme.colors.secondary.light};

    transform-origin: top;
    animation: slide-down 0.1s ease;
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
