import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { MakeOptional } from '@custom-types';

import type { ThemeColor } from '@styles/theme';

type DividerProps = {
  space: number;
  color: ThemeColor;
};

const Divider = styled.div<MakeOptional<DividerProps, 'space' | 'color'>>(({ space = 1, color, theme }) => {
  const defaultColor = theme.colors.secondary.dark;
  return css`
    height: 1px;
    background-color: ${color ? color : defaultColor};
    margin-top: ${space * 10}px;
    margin-bottom: ${space * 10}px;
  `;
});

export default Divider;
