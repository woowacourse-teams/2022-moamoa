import { Link } from 'react-router-dom';

import styled from '@emotion/styled';

import { PATH } from '@constants';

import { changeDateSeperator } from '@utils';

import type { CommunityArticleDetail } from '@custom-types';

import { applyHoverBgTransitionStyle } from '@styles/theme';

import ListItem from '@shared/list-item/ListItem';

export type NoticeArticleListItemProps = Pick<CommunityArticleDetail, 'id' | 'title' | 'author' | 'createdDate'>;

const NoticeArticleListItem: React.FC<NoticeArticleListItemProps> = ({ id, title, author, createdDate }) => {
  return (
    <Self>
      <Link to={PATH.NOTICE_ARTICLE(id)}>
        <ListItem
          title={title}
          userInfo={{
            src: author.imageUrl,
            username: author.username,
          }}
          subInfo={changeDateSeperator(createdDate)}
        />
      </Link>
    </Self>
  );
};

export default NoticeArticleListItem;

const Self = styled.li`
  ${({ theme }) => applyHoverBgTransitionStyle(theme)}
`;
