import { CssLength } from '@custom-types';

import * as S from '@components/flex/Flex.style';

export type FlexProps = {
  children: React.ReactNode;
  width?: CssLength;
  height?: CssLength;
  gap?: CssLength;
  direction?: 'row' | 'column';
  alignItems?: 'flex-start' | 'space-between' | 'center' | 'flex-end';
  justifyContent?: FlexProps['alignItems'];
  rowGap?: CssLength;
  grow?: boolean;
};

const Flex: React.FC<FlexProps> = ({
  children,
  width,
  height,
  gap,
  direction,
  alignItems,
  justifyContent,
  rowGap,
  grow,
}) => (
  <S.Flex
    width={width}
    height={height}
    gap={gap}
    direction={direction}
    alignItems={alignItems}
    justifyContent={justifyContent}
    rowGap={rowGap}
    grow={grow}
  >
    {children}
  </S.Flex>
);

export default Flex;
