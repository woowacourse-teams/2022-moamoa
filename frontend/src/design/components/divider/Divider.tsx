import type { CssLength } from '@custom-types';

import { type ThemeColor } from '@styles/theme';

import * as S from '@design/components/divider/Divider.style';

export type DividerProps = {
  orientation?: 'vertical' | 'horizontal';
  color?: ThemeColor;
  space?: CssLength;
  height?: CssLength;
  width?: CssLength;
};

const Divider: React.FC<DividerProps> = ({
  orientation = 'horizontal',
  color = '#cfd8dc',
  space = '4px',
  width = '100%',
  height = 'auto',
}) => {
  return <S.Divider orientation={orientation} color={color} space={space} width={width} height={height} />;
};

export default Divider;
