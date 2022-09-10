import type { CssLength } from '@custom-types';

import * as S from '@design/components/button-group/ButtonGroup.style';

export type ButtonGroupProps = {
  children: React.ReactNode;
  width?: CssLength;
  height?: CssLength;
  orientation?: 'vertical' | 'horizontal';
  gap?: CssLength;
};

const ButtonGroup: React.FC<ButtonGroupProps> = ({
  children,
  width = '100%',
  height = 'fit-content',
  orientation = 'horizontal',
  gap = 0,
}) => {
  return (
    <S.ButtonGroup width={width} height={height} orientation={orientation} gap={gap}>
      {children}
    </S.ButtonGroup>
  );
};

export default ButtonGroup;
