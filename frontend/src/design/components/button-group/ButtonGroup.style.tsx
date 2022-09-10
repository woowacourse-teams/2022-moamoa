import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { ButtonGroupProps } from './ButtonGroup';

type StyleButtonGroupProps = Required<Pick<ButtonGroupProps, 'orientation' | 'gap' | 'height' | 'width'>>;

export const ButtonGroup = styled.ul<StyleButtonGroupProps>`
  ${({ orientation, gap, width, height }) => css`
    display: flex;
    ${orientation === 'vertical' && 'flex-direction: column'};
    gap: ${gap};

    width: ${width};
    height: ${height};
  `}
`;
