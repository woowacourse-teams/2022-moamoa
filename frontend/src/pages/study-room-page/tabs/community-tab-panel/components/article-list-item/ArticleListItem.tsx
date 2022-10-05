import { changeDateSeperator } from '@utils';
import tw from '@utils/tw';

import { CommunityArticle } from '@custom-types';

import { theme } from '@styles/theme';

import Flex from '@components/flex/Flex';
import UserInfoItem from '@components/user-info-item/UserInfoItem';

import * as S from '@community-tab/components/article-list-item/ArticleListItem.style';

export type ArticleListItemProps = Pick<CommunityArticle, 'title' | 'author' | 'createdDate'>;

const ArticleListItem: React.FC<ArticleListItemProps> = ({ title, author, createdDate }) => {
  return (
    <S.ArticleListItem>
      <Flex alignItems="center">
        <div css={tw`flex-grow text-[${theme.fontSize.lg}]`}>
          <span>{title}</span>
        </div>
        <UserInfoItem size="md" src={author.imageUrl} name={author.username}>
          <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
          <UserInfoItem.Content>{changeDateSeperator(createdDate)}</UserInfoItem.Content>
        </UserInfoItem>
      </Flex>
    </S.ArticleListItem>
  );
};

export default ArticleListItem;
