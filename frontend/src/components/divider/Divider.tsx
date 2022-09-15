import type { CssLength } from '@custom-types';

import { type ThemeColor } from '@styles/theme';

import * as S from '@components/divider/Divider.style';

export type DividerProps = {
  orientation?: 'vertical' | 'horizontal';
  color?: ThemeColor;
  space?: CssLength;
  verticalLength?: CssLength;
  horizontalLength?: CssLength;
};

const Divider: React.FC<DividerProps> = ({
  orientation = 'horizontal',
  color = '#cfd8dc',
  space = '4px',
  horizontalLength = '100%',
  verticalLength = 'auto',
}) => {
  return (
    <S.Divider
      orientation={orientation}
      color={color}
      space={space}
      horizontalLength={horizontalLength}
      verticalLength={verticalLength}
    />
  );
};

export default Divider;
