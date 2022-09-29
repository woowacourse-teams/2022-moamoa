import styled from '@emotion/styled';

import { changeDateSeperator } from '@utils';
import tw from '@utils/tw';

import { CommunityArticle } from '@custom-types';

import { applyHoverTransitionStyle, theme } from '@styles/theme';

import Flex from '@components/flex/Flex';
import UserInfoItem from '@components/user-info-item/UserInfoItem';

<<<<<<< HEAD
=======
import * as S from '@community-tab/components/article-list-item/ArticleListItem.style';

>>>>>>> develop
export type ArticleListItemProps = Pick<CommunityArticle, 'title' | 'author' | 'createdDate'>;

const ArticleListItem: React.FC<ArticleListItemProps> = ({ title, author, createdDate }) => {
  return (
    <Self>
      <Flex alignItems="center">
        <div css={tw`flex-grow text-[${theme.fontSize.lg}]`}>
          <span>{title}</span>
        </div>
        <UserInfoItem size="md" src={author.imageUrl} name={author.username}>
          <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
          <UserInfoItem.Content>{changeDateSeperator(createdDate)}</UserInfoItem.Content>
        </UserInfoItem>
      </Flex>
    </Self>
  );
};

const Self = styled.div`
  ${applyHoverTransitionStyle()}
`;

export default ArticleListItem;
