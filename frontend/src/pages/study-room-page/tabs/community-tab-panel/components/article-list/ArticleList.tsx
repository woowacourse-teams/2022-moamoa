import { useState } from 'react';
import { Link } from 'react-router-dom';

import { PATH } from '@constants';

import type { StudyId } from '@custom-types';

import { useGetCommunityArticles } from '@api/community';

import Divider from '@components/divider/Divider';
import Flex from '@components/flex/Flex';

import ArticleListItem from '@community-tab/components/article-list-item/ArticleListItem';
import Pagination from '@community-tab/components/pagination/Pagination';

export type ArticleListProps = {
  studyId: StudyId;
};

const ArticleList: React.FC<ArticleListProps> = ({ studyId }) => {
  const [page, setPage] = useState<number>(1);
  const { isFetching, isSuccess, isError, data } = useGetCommunityArticles({ studyId, page });

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
    <Flex flexDirection="column" rowGap="20px">
      <ul>
        {articles.map(article => (
          <li key={article.id}>
            <Link to={PATH.COMMUNITY_ARTICLE(article.id)}>
              <ArticleListItem title={article.title} author={article.author} createdDate={article.createdDate} />
            </Link>
            <Divider />
          </li>
        ))}
      </ul>
      <Pagination
        count={lastPage - 1}
        defaultPage={currentPage}
        onNumberButtonClick={num => {
          setPage(num);
        }}
      />
    </Flex>
  );
};

export default ArticleList;
