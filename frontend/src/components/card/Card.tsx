import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import type { CssLength, MakeRequired } from '@custom-types';

import { type ThemeColor, type ThemeFontSize } from '@styles/theme';

export type CardProps = {
  children: React.ReactNode;
  width?: CssLength;
  height?: CssLength;
  backgroundColor?: ThemeColor | 'transparent';
  shadow?: boolean;
  gap?: CssLength;
  padding?: CssLength;
};

export type CardHeadingProps = {
  children: React.ReactNode;
  maxLine?: number;
  fontSize?: ThemeFontSize;
};

export type CardContentProps = {
  children: React.ReactNode;
  maxLine?: number;
  align?: 'right' | 'left' | 'center';
  fontSize?: ThemeFontSize;
};

const Card: React.FC<CardProps> = ({
  children,
  width = '100%',
  height = 'fit-content',
  backgroundColor = 'transparent',
  shadow,
  gap,
  padding,
}) => {
  return (
    <CardSelf
      width={width}
      height={height}
      backgroundColor={backgroundColor}
      shadow={shadow}
      gap={gap}
      padding={padding}
    >
      {children}
    </CardSelf>
  );
};

const CardHeading: React.FC<CardHeadingProps> = ({ children, maxLine = 1, fontSize = 'lg' }) => {
  return (
    <CardHeadingSelf maxLine={maxLine} fontSize={fontSize}>
      {children}
    </CardHeadingSelf>
  );
};

const CardContent: React.FC<CardContentProps> = ({ children, maxLine = 2, align = 'left', fontSize = 'sm' }) => {
  return (
    <CardContentSelf maxLine={maxLine} align={align} fontSize={fontSize}>
      {children}
    </CardContentSelf>
  );
};

type StyledCardProps = MakeRequired<
  Pick<CardProps, 'height' | 'width' | 'backgroundColor' | 'shadow' | 'gap' | 'padding'>,
  'width' | 'height' | 'backgroundColor'
>;

type StyledCardHeadingProps = Required<Pick<CardHeadingProps, 'maxLine' | 'fontSize'>>;

type StyledCardContentProps = Required<Pick<CardContentProps, 'maxLine' | 'align' | 'fontSize'>>;

export const CardSelf = styled.div<StyledCardProps>`
  ${({ theme, width, height, backgroundColor, shadow, gap, padding }) => css`
    display: flex;
    flex-direction: column;
    ${gap && `gap: ${gap}`};

    width: ${width};
    height: ${height};
    ${padding && `padding: ${padding}`};
    overflow: hidden;

    background-color: ${backgroundColor};
    border-radius: ${theme.radius.sm};
    ${shadow && `box-shadow: 0 0 2px 1px ${theme.colors.secondary.base};`}
  `}
`;

export const CardHeadingSelf = styled.h1<StyledCardHeadingProps>`
  ${({ theme, maxLine, fontSize }) => css`
    width: 100%;
    margin-bottom: 8px;

    font-size: ${theme.fontSize[fontSize]};
    font-weight: ${theme.fontWeight.bold};
    line-height: ${theme.fontSize[fontSize]};

    ${nLineEllipsis(maxLine)};
  `}
`;

export const CardContentSelf = styled.p<StyledCardContentProps>`
  ${({ theme, maxLine, align, fontSize }) => css`
    width: 100%;
    height: calc(${theme.fontSize[fontSize]} * ${maxLine});

    font-size: ${theme.fontSize[fontSize]};
    line-height: ${theme.fontSize[fontSize]};
    text-align: ${align};

    ${nLineEllipsis(maxLine)};
  `}
`;

export default Object.assign(Card, {
  Heading: CardHeading,
  Content: CardContent,
});
