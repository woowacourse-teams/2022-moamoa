import type { CssLength } from '@custom-types';

import { type ThemeColor, type ThemeFontSize } from '@styles/theme';

import * as S from '@components/card/Card.style';

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
    <S.Card width={width} height={height} backgroundColor={backgroundColor} shadow={shadow} gap={gap} padding={padding}>
      {children}
    </S.Card>
  );
};

const CardHeading: React.FC<CardHeadingProps> = ({ children, maxLine = 1, fontSize = 'lg' }) => {
  return (
    <S.CardHeading maxLine={maxLine} fontSize={fontSize}>
      {children}
    </S.CardHeading>
  );
};

const CardContent: React.FC<CardContentProps> = ({ children, maxLine = 2, align = 'left', fontSize = 'sm' }) => {
  return (
    <S.CardContent maxLine={maxLine} align={align} fontSize={fontSize}>
      {children}
    </S.CardContent>
  );
};

export default Object.assign(Card, {
  Heading: CardHeading,
  Content: CardContent,
});
