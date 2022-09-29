import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { FlexProps } from './Flex';

type StyledFlexProps = Omit<FlexProps, 'children'>;

export const Flex = styled.div<StyledFlexProps>`
  ${({ width, height, gap, alignItems, justifyContent, direction, rowGap, grow, wrap }) => css`
    display: flex;
    ${direction && `flex-direction: ${direction}`};
    ${width && `width: ${width}`};
    ${height && `height: ${height}`};
    ${gap && `column-gap: ${gap}`};
    ${alignItems && `align-items: ${alignItems}`};
    ${justifyContent && `justify-content: ${justifyContent}`};
    ${rowGap && `row-gap: ${rowGap}`};
    ${grow && `flex-grow: 1`};
    ${wrap && `flex-wrap: wrap`};
  `}
`;
