import type { CssLength } from '@custom-types';

import * as S from '@design/components/card/Card.style';

export type CardProps = {
  children: React.ReactNode;
  width?: CssLength;
  height: CssLength;
};

const Card: React.FC<CardProps> = ({ children, width = '100%', height }) => {
  return (
    <S.Card width={width} height={height}>
      {children}
    </S.Card>
  );
};

export default Card;
