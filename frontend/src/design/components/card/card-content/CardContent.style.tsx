import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import { type CardContentProps } from '@design/components/card/card-content/CardContent';

type StyleCardContentProps = Required<Pick<CardContentProps, 'maxLine' | 'align'>>;

export const CardContent = styled.p<StyleCardContentProps>`
  ${({ theme, maxLine, align }) => css`
    width: 100%;

    font-size: ${theme.fontSize.sm};
    line-height: ${theme.fontSize.sm};
    text-align: ${align};

    ${nLineEllipsis(maxLine)};
  `}
`;
