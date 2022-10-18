import styled from '@emotion/styled';

import { changeDateSeperator } from '@utils';

import type { CommunityArticle } from '@custom-types';

import { applyHoverTransitionStyle } from '@styles/theme';

import ListItem from '@shared/list-item/ListItem';

export type ArticleListItemProps = Pick<CommunityArticle, 'title' | 'author' | 'createdDate'>;

const ArticleListItem: React.FC<ArticleListItemProps> = ({ title, author, createdDate }) => {
  return (
    <Self>
      <ListItem
        title={title}
        userInfo={{
          src: author.imageUrl,
          username: author.username,
        }}
        subInfo={changeDateSeperator(createdDate)}
      />
    </Self>
  );
};

export default ArticleListItem;

const Self = styled.li`
  ${applyHoverTransitionStyle()}
`;
