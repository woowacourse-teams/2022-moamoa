import { ReactNode } from 'react';

import * as S from '@components/card/Card.style';

export interface CardProps {
  thumbnailUrl: string;
  thumbnailAlt: string;
  title: string;
  excerpt: string;
  extraChips: Array<ReactNode>;
}

const Card: React.FC<CardProps> = ({ thumbnailUrl, thumbnailAlt, title, excerpt, extraChips }) => {
  return (
    <S.Card>
      <S.ImageContainer>
        <S.Image src={thumbnailUrl} alt={thumbnailAlt} />
      </S.ImageContainer>
      <S.Contents>
        <S.Title>{title}</S.Title>
        <S.Excerpt>{excerpt}</S.Excerpt>
        <S.Extra>{extraChips}</S.Extra>
      </S.Contents>
    </S.Card>
  );
};

export default Card;
