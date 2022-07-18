import { ReactNode } from 'react';

import * as S from './Card.style';

export interface CardProps {
  thumbnailUrl: string;
  thumbnailAlt: string;
  title: string;
  description: string;
  extraChips: Array<ReactNode>;
}

const Card: React.FC<CardProps> = ({ thumbnailUrl, thumbnailAlt, title, description, extraChips }) => {
  return (
    <S.Card>
      <S.ImageContainer>
        <S.Image src={thumbnailUrl} alt={thumbnailAlt} />
      </S.ImageContainer>
      <S.Contents>
        <S.Title>{title}</S.Title>
        <S.Description>{description}</S.Description>
        <S.Extra>{extraChips}</S.Extra>
      </S.Contents>
    </S.Card>
  );
};

export default Card;
