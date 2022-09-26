import { CommunityArticle } from '@custom-types';

import Avatar from '@components/avatar/Avatar';

import * as S from '@study-room-page/tabs/notice-tab-panel/components/article-list-item/ArticleListItem.style';

export type ArticleListItemProps = CommunityArticle;

const ArticleListItem: React.FC<ArticleListItemProps> = ({ title, author, createdDate }) => {
  return (
    <S.ArticleListItem>
      <S.Main>
        <S.Title>{title}</S.Title>
        <S.Content>{}</S.Content>
      </S.Main>
      <S.MoreInfo>
        <S.Author>
          <Avatar size="xs" profileImg={author.imageUrl} profileAlt={author.username} />
          <S.Username>{author.username}</S.Username>
        </S.Author>
        <S.Date>
          <div>작성일</div>
          <div>{createdDate}</div>
        </S.Date>
      </S.MoreInfo>
    </S.ArticleListItem>
  );
};

export default ArticleListItem;
