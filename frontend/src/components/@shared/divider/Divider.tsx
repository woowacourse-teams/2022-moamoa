import { css, useTheme } from '@emotion/react';
import styled from '@emotion/styled';

import type { CssLength } from '@custom-types';

import { type ThemeColor } from '@styles/theme';

export type DividerProps = {
  orientation?: 'vertical' | 'horizontal';
  color?: ThemeColor;
  space?: CssLength;
  verticalLength?: CssLength;
  horizontalLength?: CssLength;
};

const Divider: React.FC<DividerProps> = ({
  orientation = 'horizontal',
  color,
  space = '4px',
  horizontalLength = '100%',
  verticalLength = 'auto',
}) => {
  const theme = useTheme();
  return (
    <Self
      orientation={orientation}
      color={color ?? theme.colors.secondary.base}
      space={space}
      horizontalLength={horizontalLength}
      verticalLength={verticalLength}
    />
  );
};

export default Divider;

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

const Self = styled.div<StyledDividerProps>`
  ${({ orientation, color, space, horizontalLength, verticalLength }) => css`
    width: ${horizontalLength};
    height: ${verticalLength};
    ${orientation === 'vertical' ? applyVerticalStyle(space, color) : applyHorizontalStyle(space, color)};
  `}
`;
