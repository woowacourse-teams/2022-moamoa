import { useState } from 'react';
import { Link, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import tw from '@utils/tw';

import { useGetCommunityArticles } from '@api/community';

import ArticleListItem from '@study-room-page/tabs/community-tab-panel/components/article-list-item/ArticleListItem';
import * as S from '@study-room-page/tabs/community-tab-panel/components/article-list/ArticleList.style';

import Pagination from '@community-tab/components/pagination/Pagination';

type ArticleListProps = {
  className?: string;
};

const ArticleList: React.FC<ArticleListProps> = ({ className }) => {
  const { studyId } = useParams<{ studyId: string }>();
  const numStudyId = Number(studyId);
  const [page, setPage] = useState<number>(1);
  const { isFetching, isSuccess, isError, data } = useGetCommunityArticles(numStudyId, page);

  if (isFetching) {
    return <div>Loading...</div>;
  }

  if (isError || !isSuccess) {
    return <div>에러가 발생했습니다</div>;
  }

  const { articles, lastPage, currentPage } = data;

  if (articles.length === 0) {
    return <div>게시글이 없습니다</div>;
  }

  return (
    <S.Container>
      <S.ArticleList>
        {articles.map(article => (
          <Link key={article.id} to={PATH.COMMUNITY_ARTICLE(studyId, article.id)}>
            <ArticleListItem key={article.id} {...article}></ArticleListItem>
          </Link>
        ))}
      </S.ArticleList>
      <Pagination
        count={lastPage - 1}
        defaultPage={currentPage}
        onNumberButtonClick={num => {
          setPage(num);
        }}
      />
    </S.Container>
  );
};

export default ArticleList;
