import { changeDateSeperator } from '@utils';
import tw from '@utils/tw';

import { CommunityArticle } from '@custom-types';

import { theme } from '@styles/theme';

import Flex from '@components/flex/Flex';
import Item from '@components/item/Item';

import * as S from '@study-room-page/tabs/community-tab-panel/components/article-list-item/ArticleListItem.style';

export type ArticleListItemProps = Pick<CommunityArticle, 'title' | 'author' | 'createdDate'>;

const ArticleListItem: React.FC<ArticleListItemProps> = ({ title, author, createdDate }) => {
  return (
    <S.ArticleListItem>
      <Flex alignItems="center">
        <div css={tw`flex-grow text-[${theme.fontSize.lg}]`}>
          <span>{title}</span>
        </div>
        <Item size="md" src={author.imageUrl} name={author.username}>
          <Item.Heading>{author.username}</Item.Heading>
          <Item.Content>{changeDateSeperator(createdDate)}</Item.Content>
        </Item>
      </Flex>
    </S.ArticleListItem>
  );
};

export default ArticleListItem;
