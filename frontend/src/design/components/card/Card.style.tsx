import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import type { MakeRequired } from '@custom-types';

import { type CardContentProps, type CardHeadingProps, type CardProps } from '@design/components/card/Card';

type StyleCardProps = MakeRequired<Pick<CardProps, 'height' | 'width'>, 'width'>;

type StyleCardHeadingProps = Required<Pick<CardHeadingProps, 'maxLine'>>;

type StyleCardContentProps = Required<Pick<CardContentProps, 'maxLine' | 'align'>>;

export const Card = styled.div<StyleCardProps>`
  ${({ width, height }) => css`
    display: flex;
    flex-direction: column;

    width: ${width};
    height: ${height};
    overflow: hidden;

    background-color: transparent;
  `}
`;

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

export const CardContent = styled.p<StyleCardContentProps>`
  ${({ theme, maxLine, align }) => css`
    width: 100%;
    height: calc(${theme.fontSize.sm} * ${maxLine});

    font-size: ${theme.fontSize.sm};
    line-height: ${theme.fontSize.sm};
    text-align: ${align};

    ${nLineEllipsis(maxLine)};
  `}
`;
