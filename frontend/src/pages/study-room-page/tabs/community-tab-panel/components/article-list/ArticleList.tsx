import { useState } from 'react';
import { Link } from 'react-router-dom';

import { css } from '@emotion/react';

import { PATH } from '@constants';

import type { StudyId } from '@custom-types';

import { ApiCommunityArticles, useGetCommunityArticles } from '@api/community';

import Divider from '@shared/divider/Divider';
import Flex from '@shared/flex/Flex';

import ArticleListItem from '@community-tab/components/article-list-item/ArticleListItem';
import Pagination from '@community-tab/components/pagination/Pagination';

export type ArticleListProps = {
  studyId: StudyId;
};

const ArticleList: React.FC<ArticleListProps> = ({ studyId }) => {
  const [page, setPage] = useState<number>(1);
  const { isFetching, isSuccess, isError, data } = useGetCommunityArticles({ studyId, page });

  return (
    <Flex flexDirection="column" rowGap="20px">
      {(() => {
        if (isFetching) return <Loading />;
        if (isError || !isSuccess) return <Error />;
        if (data.articles.length === 0) return <NoArticle />;
        return (
          <>
            <Self articles={data.articles} />
            <Pagination
              count={data.lastPage - 1}
              defaultPage={data.currentPage}
              onNumberButtonClick={num => {
                setPage(num);
              }}
            />
          </>
        );
      })()}
    </Flex>
  );
};

const Loading = () => <div>Loading...</div>;

const Error = () => <div>에러가 발생했습니다</div>;

const NoArticle = () => <div>게시글이 없습니다</div>;

type SelfProps = {
  articles: ApiCommunityArticles['get']['responseData']['articles'];
};
const Self: React.FC<SelfProps> = ({ articles }) => (
  <ul
    css={css`
      width: 100%;
    `}
  >
    {articles.map(article => (
      <li key={article.id}>
        <Link to={PATH.COMMUNITY_ARTICLE(article.id)}>
          <ArticleListItem title={article.title} author={article.author} createdDate={article.createdDate} />
        </Link>
        <Divider />
      </li>
    ))}
  </ul>
);

export default ArticleList;
