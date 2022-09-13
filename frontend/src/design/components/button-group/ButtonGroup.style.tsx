import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { MakeRequired } from '@custom-types';

import { ButtonGroupProps } from './ButtonGroup';

type StyleButtonGroupProps = MakeRequired<
  Pick<ButtonGroupProps, 'orientation' | 'gap' | 'height' | 'width' | 'justifyContent' | 'alignItems'>,
  'gap' | 'height' | 'orientation' | 'width'
>;

export const ButtonGroup = styled.ul<StyleButtonGroupProps>`
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
