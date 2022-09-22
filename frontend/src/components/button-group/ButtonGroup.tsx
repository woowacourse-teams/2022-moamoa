import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { CssLength, MakeRequired } from '@custom-types';

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
    <Self
      width={width}
      height={height}
      orientation={orientation}
      gap={gap}
      justifyContent={justifyContent}
      alignItems={alignItems}
    >
      {children}
    </Self>
  );
};

type StyledButtonGroupProps = MakeRequired<
  Pick<ButtonGroupProps, 'orientation' | 'gap' | 'height' | 'width' | 'justifyContent' | 'alignItems'>,
  'gap' | 'height' | 'orientation' | 'width'
>;

export const Self = styled.div<StyledButtonGroupProps>`
  ${({ orientation, gap, width, height, justifyContent, alignItems }) => css`
    display: flex;
    ${orientation === 'vertical' && 'flex-direction: column;'}
    gap: ${gap};
    ${justifyContent && `justify-content: ${justifyContent};`}
    ${alignItems && `align-items: ${alignItems};`}

    width: ${width};
    height: ${height};
  `}
`;

export default ButtonGroup;
