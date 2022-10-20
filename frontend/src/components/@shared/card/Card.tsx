import { css, useTheme } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import type { MakeRequired } from '@custom-types';

import { type CustomCSS, resolveCustomCSS } from '@styles/custom-css';
import { type ThemeColor } from '@styles/theme';

export type CardProps = {
  children: React.ReactNode;
  backgroundColor?: ThemeColor | 'transparent';
  shadow?: boolean;
  custom?: CustomCSS<'width' | 'height' | 'padding' | 'gap' | 'position'>;
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
  const theme = useTheme();
  return (
    <CardSelf css={resolveCustomCSS(custom, theme)} backgroundColor={backgroundColor} shadow={shadow}>
      {children}
    </CardSelf>
  );
};

const CardHeading: React.FC<CardHeadingProps> = ({ custom, children, maxLine = 1 }) => {
  const theme = useTheme();
  return (
    <CardHeadingSelf css={resolveCustomCSS(custom, theme)} maxLine={maxLine}>
      {children}
    </CardHeadingSelf>
  );
};

const CardContent: React.FC<CardContentProps> = ({ custom, children, maxLine = 2, align = 'left' }) => {
  const theme = useTheme();
  return (
    <CardContentSelf css={resolveCustomCSS(custom, theme)} maxLine={maxLine} align={align}>
      {children}
    </CardContentSelf>
  );
};

type StyledCardProps = MakeRequired<Pick<CardProps, 'backgroundColor' | 'shadow'>, 'backgroundColor'>;

type StyledCardHeadingProps = Required<Pick<CardHeadingProps, 'maxLine'>>;

type StyledCardContentProps = Required<Pick<CardContentProps, 'maxLine' | 'align'>>;

const CardSelf = styled.div<StyledCardProps>`
  ${({ theme, backgroundColor, shadow }) => css`
    display: flex;
    flex-direction: column;

    width: 100%;
    height: fit-content;
    overflow: hidden;

    background-color: ${backgroundColor};
    border-radius: ${theme.radius.sm};
    ${shadow && `box-shadow: 0 0 2px 1px ${theme.colors.secondary.base};`}
  `}
`;

const CardHeadingSelf = styled.div<StyledCardHeadingProps>`
  ${({ theme, maxLine }) => css`
    width: 100%;
    margin-bottom: 8px;

    font-size: ${theme.fontSize.lg};
    font-weight: ${theme.fontWeight.bold};

    ${nLineEllipsis(maxLine)};
  `}
`;

const CardContentSelf = styled.div<StyledCardContentProps>`
  ${({ theme, maxLine, align }) => css`
    width: 100%;

    font-size: ${theme.fontSize.sm};
    text-align: ${align};

    ${nLineEllipsis(maxLine)};
  `}
`;

export default Object.assign(Card, {
  Heading: CardHeading,
  Content: CardContent,
});
