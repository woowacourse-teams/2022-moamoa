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

const HiOutlineTrash = () => (
  <svg
    stroke="currentColor"
    fill="none"
    strokeWidth="0"
    viewBox="0 0 24 24"
    height="20"
    width="20"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
    ></path>
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
          <S.TrashButton>
            <HiOutlineTrash />
          </S.TrashButton>
        </S.Bottom>
      </S.Container>
    </S.MyStudyCard>
  );
};

export default MyStudyCard;
