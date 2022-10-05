import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import type { MakeRequired } from '@custom-types';

import { CustomCSS, resolveCustomCSS } from '@styles/custom-css';
import { type ThemeColor } from '@styles/theme';

export type CardProps = {
  children: React.ReactNode;
  backgroundColor?: ThemeColor | 'transparent';
  shadow?: boolean;
  custom?: CustomCSS<'width' | 'height' | 'padding' | 'gap'>;
};

export type CardHeadingProps = {
  children: React.ReactNode;
  maxLine?: number;
  custom?: CustomCSS<'marginBottom' | 'fontSize'>;
};

export type CardContentProps = {
  children: React.ReactNode;
  maxLine?: number;
  align?: 'right' | 'left' | 'center';
  custom?: CustomCSS<'fontSize' | 'padding' | 'gap'>;
};

const Card: React.FC<CardProps> = ({ custom, children, backgroundColor = 'transparent', shadow }) => {
  return (
    <CardSelf css={resolveCustomCSS(custom)} backgroundColor={backgroundColor} shadow={shadow}>
      {children}
    </CardSelf>
  );
};

const CardHeading: React.FC<CardHeadingProps> = ({ custom, children, maxLine = 1 }) => {
  return (
    <CardHeadingSelf css={resolveCustomCSS(custom)} maxLine={maxLine}>
      {children}
    </CardHeadingSelf>
  );
};

const CardContent: React.FC<CardContentProps> = ({ custom, children, maxLine = 2, align = 'left' }) => {
  return (
    <CardContentSelf css={resolveCustomCSS(custom)} maxLine={maxLine} align={align}>
      {children}
    </CardContentSelf>
  );
};

type StyledCardProps = MakeRequired<Pick<CardProps, 'backgroundColor' | 'shadow'>, 'backgroundColor'>;

type StyledCardHeadingProps = Required<Pick<CardHeadingProps, 'maxLine'>>;

type StyledCardContentProps = Required<Pick<CardContentProps, 'maxLine' | 'align'>>;

export const CardSelf = styled.div<StyledCardProps>`
  ${({ theme, backgroundColor, shadow }) => css`
    display: flex;
    width: 100%;
    height: fit-content;
    flex-direction: column;

    overflow: hidden;

    background-color: ${backgroundColor};
    border-radius: ${theme.radius.sm};
    ${shadow && `box-shadow: 0 0 2px 1px ${theme.colors.secondary.base};`}
  `}
`;

export const CardHeadingSelf = styled.h1<StyledCardHeadingProps>`
  ${({ theme, maxLine }) => css`
    width: 100%;

    font-size: ${theme.fontSize.lg};
    font-weight: ${theme.fontWeight.bold};
    line-height: ${theme.fontSize.lg};

    ${nLineEllipsis(maxLine)};
  `}
`;

export const CardContentSelf = styled.p<StyledCardContentProps>`
  ${({ theme, maxLine, align }) => css`
    width: 100%;
    height: calc(${theme.fontSize.sm} * ${maxLine});

    font-size: ${theme.fontSize.sm};
    line-height: ${theme.fontSize.sm};
    text-align: ${align};

    ${nLineEllipsis(maxLine)};
  `}
`;

export default Object.assign(Card, {
  Heading: CardHeading,
  Content: CardContent,
});
