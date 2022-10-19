import { Link } from 'react-router-dom';

import styled from '@emotion/styled';

import { PATH } from '@constants';

import { changeDateSeperator } from '@utils';

import type { DraftArtcle } from '@custom-types';

import { applyHoverBgTransitionStyle } from '@styles/theme';

import { IconButton } from '@shared/button';
import Flex from '@shared/flex/Flex';
import { TrashcanIcon } from '@shared/icons';
import ListItem from '@shared/list-item/ListItem';

export type DraftListItemProps = Pick<DraftArtcle, 'id' | 'title' | 'createdDate'> & {
  onDeleteDraftItemClick: React.MouseEventHandler<HTMLButtonElement>;
};

const DraftListItem: React.FC<DraftListItemProps> = ({
  id,
  title,
  createdDate,
  onDeleteDraftItemClick: handleDeleteDraftItemClick,
}) => {
  return (
    <Self>
      <Flex columnGap="16px" alignItems="center">
        <IconButton ariaLabel="삭제" variant="secondary" onClick={handleDeleteDraftItemClick}>
          <TrashcanIcon />
        </IconButton>
        <Flex.Item flexGrow={1}>
          <Link to={`${PATH.STUDY_ROOM(0)}/${PATH.COMMUNITY}/${PATH.DRAFT_COMMUNITY_PUBLISH(id)}`}>
            <ListItem title={title} subInfo={changeDateSeperator(createdDate)} custom={{ padding: '16px 0' }} />
          </Link>
        </Flex.Item>
      </Flex>
    </Self>
  );
};

export default DraftListItem;

const Self = styled.li`
  ${({ theme }) => applyHoverBgTransitionStyle(theme)}
`;
