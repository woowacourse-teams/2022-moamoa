import type { CssLength } from '@custom-types';

import * as S from '@design/components/card/Card.style';

export type CardProps = {
  children: React.ReactNode;
  width?: CssLength;
  height: CssLength;
};

export type CardHeadingProps = {
  children: string;
  maxLine?: number;
};

export type CardContentProps = {
  children: React.ReactNode;
  maxLine?: number;
  align?: 'right' | 'left' | 'center';
};

const Card: React.FC<CardProps> = ({ children, width = '100%', height }) => {
  return (
    <S.Card width={width} height={height}>
      {children}
    </S.Card>
  );
};

const CardHeading: React.FC<CardHeadingProps> = ({ children, maxLine = 1 }) => {
  return <S.CardHeading maxLine={maxLine}>{children}</S.CardHeading>;
};

const CardContent: React.FC<CardContentProps> = ({ children, maxLine = 2, align = 'left' }) => {
  return (
    <S.CardContent maxLine={maxLine} align={align}>
      {children}
    </S.CardContent>
  );
};

export default Object.assign(Card, {
  Heading: CardHeading,
  Content: CardContent,
});
