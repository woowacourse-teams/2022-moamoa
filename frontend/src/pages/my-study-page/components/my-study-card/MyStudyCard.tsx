import tw from '@utils/tw';

import type { Tag } from '@custom-types';

import { IconButton } from '@components/button';
import Card from '@components/card/Card';
import { CrownIcon, TrashcanIcon } from '@components/icons';

import * as S from '@my-study-page/components/my-study-card/MyStudyCard.style';

export type MyStudyCardProps = {
  title: string;
  ownerName: string;
  tags: Array<Pick<Tag, 'id' | 'name'>>;
  startDate: string;
  endDate?: string;
  done?: boolean;
  onQuitStudyButtonClick: React.MouseEventHandler<HTMLButtonElement>;
};

const MyStudyCard: React.FC<MyStudyCardProps> = ({
  title,
  ownerName,
  tags,
  startDate,
  endDate,
  done = false,
  onQuitStudyButtonClick: handleQuitStudyButtonClick,
}) => {
  return (
    <S.MyStudyCard done={done}>
      <Card gap="8px" padding="16px" shadow>
        <Card.Heading>{title}</Card.Heading>
        <Card.Content fontSize="md">
          <CrownIcon />
          <span>{ownerName}</span>
        </Card.Content>
        <Card.Content maxLine={1} fontSize="md">
          {tags.map(tag => (
            <span key={tag.id} css={tw`mr-8`}>
              #{tag.name}
            </span>
          ))}
        </Card.Content>
        <Card.Content fontSize="md">
          <span>{startDate}</span> ~ <span>{endDate || ''}</span>
        </Card.Content>
      </Card>
      <div css={tw`absolute bottom-12 right-12 z-3`}>
        <IconButton
          variant="secondary"
          onClick={handleQuitStudyButtonClick}
          ariaLabel="스터디 탈퇴"
          width="auto"
          height="auto"
        >
          <TrashcanIcon />
        </IconButton>
      </div>
    </S.MyStudyCard>
  );
};

export default MyStudyCard;
