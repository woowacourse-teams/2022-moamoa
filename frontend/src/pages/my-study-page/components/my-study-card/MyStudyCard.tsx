import type { MakeOptional, Tag } from '@custom-types';

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

const TbCrown = () => (
  <svg
    stroke="currentColor"
    fill="none"
    strokeWidth="2"
    viewBox="0 0 24 24"
    strokeLinecap="round"
    strokeLinejoin="round"
    height="20"
    width="20"
    xmlns="http://www.w3.org/2000/svg"
  >
    <desc></desc>
    <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
    <path d="M12 6l4 6l5 -4l-2 10h-14l-2 -10l5 4z"></path>
  </svg>
);

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
            <TbCrown />
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
