import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type LabelProps } from '@components/label/Label';

type StyleLabelProps = Pick<LabelProps, 'hidden'>;

export const Label = styled.label<StyleLabelProps>`
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
