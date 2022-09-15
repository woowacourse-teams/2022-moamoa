import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type LabelProps } from '@components/label/Label';

type StyledLabelProps = Pick<LabelProps, 'hidden'>;

export const Label = styled.label<StyledLabelProps>`
  ${({ hidden }) => css`
    ${hidden &&
    css`
      display: block;

      height: 0;
      width: 0;

      visibility: hidden;
    `}
  `}
`;
