import type { MakeOptional, Tag } from '@custom-types';

import { CrownSvg } from '@components/svg';

import * as S from '@my-study-page/components/my-study-card/MyStudyCard.style';

export type MyStudyCardProps = {
  title: string;
  ownerName: string;
  tags: Array<Pick<Tag, 'id' | 'name'>>;
  startDate: string;
  endDate?: string;
  disabled: boolean;
};

type OptionalMyStudyCardProps = MakeOptional<MyStudyCardProps, 'disabled'>;

const MyStudyCard: React.FC<OptionalMyStudyCardProps> = ({
  title,
  ownerName,
  tags,
  startDate,
  endDate,
  disabled = false,
}) => {
  return (
    <S.MyStudyCard disabled={disabled}>
      <S.Container>
        <S.Top>
          <S.Title>{title}</S.Title>
          <S.Owner>
            <CrownSvg />
            {ownerName}
          </S.Owner>
          <S.Tags>
            {tags.map(tag => (
              <li key={tag.id}>#{tag.name}</li>
            ))}
          </S.Tags>
        </S.Top>
        <S.Bottom>
          <S.Period>
            <span>{startDate}</span> ~ <span>{endDate || ''}</span>
          </S.Period>
        </S.Bottom>
      </S.Container>
    </S.MyStudyCard>
  );
};

export default MyStudyCard;
