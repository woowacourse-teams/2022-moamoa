import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { CssLength } from '@custom-types';

import { ThemeColor } from '@styles/theme';

import { DividerProps } from '@components/divider/Divider';

type StyledDividerProps = Required<DividerProps>;

const applyVerticalStyle = (space: CssLength, color: ThemeColor) => css`
  width: 100%;
  min-height: 100%;
  margin: 0 ${space};

  border-left: 1px solid ${color};
`;

const applyHorizontalStyle = (space: CssLength, color: ThemeColor) => css`
  height: 0;
  margin: ${space} 0;

  border-bottom: 1px solid ${color};
`;

export const Divider = styled.div<StyledDividerProps>`
  ${({ orientation, color, space, horizontalLength, verticalLength }) => css`
    width: ${horizontalLength};
    height: ${verticalLength};
    ${orientation === 'vertical' ? applyVerticalStyle(space, color) : applyHorizontalStyle(space, color)};
  `}
`;
