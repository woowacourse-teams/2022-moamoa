import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type DropDownBoxProps } from '@components/drop-down-box/DropDownBox';

type StyledDropDownBox = Pick<DropDownBoxProps, 'top' | 'bottom' | 'left' | 'right' | 'padding'>;

export const DropDownBox = styled.div<StyledDropDownBox>`
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
