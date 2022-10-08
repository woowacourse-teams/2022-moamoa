import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type CssLength, type MakeRequired } from '@custom-types';

import { type CustomCSS, resolveCustomCSS } from '@styles/custom-css';

export type ButtonGroupProps = {
  children: React.ReactNode;
  orientation?: 'vertical' | 'horizontal';
  justifyContent?: 'flex-start' | 'flex-end' | 'center' | 'space-between';
  alignItems?: 'flex-start' | 'flex-end' | 'center' | 'space-between';
  gap?: CssLength;
  custom?: CustomCSS<'width' | 'height'>;
};

const ButtonGroup: React.FC<ButtonGroupProps> = ({
  children,
  orientation = 'horizontal',
  justifyContent,
  alignItems,
  gap = 0,
  custom,
}) => {
  return (
    <Self
      css={resolveCustomCSS(custom)}
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
  Pick<ButtonGroupProps, 'orientation' | 'gap' | 'justifyContent' | 'alignItems'>,
  'gap' | 'orientation'
>;

export const Self = styled.div<StyledButtonGroupProps>`
  ${({ orientation, gap, justifyContent, alignItems }) => css`
    display: flex;
    ${orientation === 'vertical' && 'flex-direction: column;'}
    gap: ${gap};
    ${justifyContent && `justify-content: ${justifyContent};`}
    ${alignItems && `align-items: ${alignItems};`}

    width: 100%;
    height: fit-content;
  `}
`;

export default ButtonGroup;
