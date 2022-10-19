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

import Chip from '@components/@shared/chip/Chip';

export type DraftListItemProps = Pick<DraftArtcle, 'id' | 'title' | 'createdDate' | 'study'> & {
  onDeleteDraftItemClick: React.MouseEventHandler<HTMLButtonElement>;
};

const DraftListItem: React.FC<DraftListItemProps> = ({
  id,
  title,
  createdDate,
  study,
  onDeleteDraftItemClick: handleDeleteDraftItemClick,
}) => {
  return (
    <Self>
      <Flex columnGap="16px" alignItems="center">
        <IconButton ariaLabel="삭제" variant="secondary" onClick={handleDeleteDraftItemClick}>
          <TrashcanIcon />
        </IconButton>
        <Flex.Item>
          <Link to={`${PATH.STUDY_ROOM(study.id)}/${PATH.COMMUNITY}/${PATH.DRAFT_COMMUNITY_PUBLISH(id)}`}>
            <Flex alignItems="center" justifyContent="space-between" columnGap="8px">
              <Chip variant="secondary">{study.title}</Chip>
              <Flex.Item>
                <ListItem title={title} subInfo={changeDateSeperator(createdDate)} custom={{ padding: '16px 0' }} />
              </Flex.Item>
            </Flex>
          </Link>
        </Flex.Item>
      </Flex>
    </Self>
  );
};

export default DraftListItem;

const Self = styled.li`
  padding: 0 8px;
  ${({ theme }) => applyHoverBgTransitionStyle(theme)}
`;
