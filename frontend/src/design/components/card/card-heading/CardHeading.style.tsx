import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import { CardContentProps } from '@design/components/card/card-content/CardContent';

type StyleCardHeadingProps = Required<Pick<CardContentProps, 'maxLine'>>;

export const CardHeading = styled.h1<StyleCardHeadingProps>`
  ${({ theme, maxLine }) => css`
    width: 100%;
    margin-bottom: 8px;

    font-size: ${theme.fontSize.lg};
    font-weight: ${theme.fontWeight.bold};
    line-height: ${theme.fontSize.lg};

    ${nLineEllipsis(maxLine)};
  `}
`;
