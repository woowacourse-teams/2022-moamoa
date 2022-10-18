import { Link } from 'react-router-dom';

import styled from '@emotion/styled';

import { PATH } from '@constants';

import { changeDateSeperator } from '@utils';

import type { CommunityArticle } from '@custom-types';

import { applyHoverTransitionStyle } from '@styles/theme';

import ListItem from '@shared/list-item/ListItem';

export type ArticleListItemProps = Pick<CommunityArticle, 'id' | 'title' | 'author' | 'createdDate'>;

const ArticleListItem: React.FC<ArticleListItemProps> = ({ id, title, author, createdDate }) => {
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

export default ArticleListItem;

const Self = styled.li`
  ${applyHoverTransitionStyle()}
`;
