import { ReactNode } from 'react';

import * as S from '@components/card/Card.style';
import CenterImage from '@components/center-image/CenterImage';

export type CardProps = {
  thumbnailUrl: string;
  thumbnailAlt: string;
  title: string;
  excerpt: string;
  extraChips: Array<ReactNode>;
};

const Card: React.FC<CardProps> = ({ thumbnailUrl, thumbnailAlt, title, excerpt, extraChips }) => {
  return (
    <S.Card>
      <S.ImageContainer>
        <CenterImage src={thumbnailUrl} alt={thumbnailAlt} />
      </S.ImageContainer>
      <S.Content>
        <S.Title>{title}</S.Title>
        <S.Excerpt>{excerpt}</S.Excerpt>
        <S.Extra>{extraChips}</S.Extra>
      </S.Content>
    </S.Card>
  );
};

export default Card;
