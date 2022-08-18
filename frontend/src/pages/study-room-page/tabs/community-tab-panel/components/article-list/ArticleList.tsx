import { CommunityArticle } from '@custom-types';

import ArticleListItem from '@study-room-page/tabs/community-tab-panel/components/article-list-item/ArticleListItem';
import * as S from '@study-room-page/tabs/community-tab-panel/components/article-list/ArticleList.style';

type ArticleListProps = {
  className?: string;
  articles: Array<CommunityArticle>;
};

const ArticleList: React.FC<ArticleListProps> = ({ className, articles }) => {
  return (
    <S.ArticleList className={className}>
      {articles.map(article => (
        <ArticleListItem key={article.id} {...article}></ArticleListItem>
      ))}
    </S.ArticleList>
  );
};

export default ArticleList;
