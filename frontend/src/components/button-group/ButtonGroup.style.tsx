import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { ButtonGroupProps } from '@components/button-group/ButtonGroup';

const dynamicStyle: Record<ButtonGroupProps['variation'], any> = {
  'flex-start': css`
    justify-content: flex-start;
  `,
  'flex-end': css`
    justify-content: flex-end;
  `,
};

export const ButtonGroup = styled.div<ButtonGroupProps>`
  ${({ variation }) => css`
    display: flex;
    ${dynamicStyle[variation]}
  `}
`;
