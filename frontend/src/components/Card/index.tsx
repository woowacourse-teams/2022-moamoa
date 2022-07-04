import Chip from '@components/Chip';

import * as S from './style';

export interface CardProps {
  thumbnailUrl: string;
  thumbnailAlt: string;
  title: string;
  description: string;
  chipText: string;
  chipDisabled: boolean;
}

const Card: React.FC<CardProps> = ({ thumbnailUrl, thumbnailAlt, title, description, chipText, chipDisabled }) => {
  return (
    <S.Card>
      <S.Image src={thumbnailUrl} alt={thumbnailAlt} />
      <S.Contents>
        <S.Title>{title}</S.Title>
        <S.Description>{description}</S.Description>
        <S.Extra>
          <Chip disabled={chipDisabled}>{chipText}</Chip>
        </S.Extra>
      </S.Contents>
    </S.Card>
  );
};

export default Card;
