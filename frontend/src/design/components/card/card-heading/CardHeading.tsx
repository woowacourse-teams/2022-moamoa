import * as S from '@design/components/card/card-heading/CardHeading.style';

export type CardHeadingProps = {
  children: string;
  maxLine?: number;
};

const CardHeading: React.FC<CardHeadingProps> = ({ children, maxLine = 1 }) => {
  return <S.CardHeading maxLine={maxLine}>{children}</S.CardHeading>;
};

export default CardHeading;
