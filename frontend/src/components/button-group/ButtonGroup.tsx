import type { CssLength } from '@custom-types';

import * as S from '@components/button-group/ButtonGroup.style';

export type ButtonGroupProps = {
  children: React.ReactNode;
  width?: CssLength;
  height?: CssLength;
  orientation?: 'vertical' | 'horizontal';
  justifyContent?: 'flex-start' | 'flex-end' | 'center' | 'space-between';
  alignItems?: 'flex-start' | 'flex-end' | 'center' | 'space-between';
  gap?: CssLength;
};

const ButtonGroup: React.FC<ButtonGroupProps> = ({
  children,
  width = '100%',
  height = 'fit-content',
  orientation = 'horizontal',
  justifyContent,
  alignItems,
  gap = 0,
}) => {
  return (
    <S.ButtonGroup
      width={width}
      height={height}
      orientation={orientation}
      gap={gap}
      justifyContent={justifyContent}
      alignItems={alignItems}
    >
      {children}
    </S.ButtonGroup>
  );
};

export default ButtonGroup;
