import { useState } from 'react';
import { Link, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { useGetCommunityArticles } from '@api/community';

import Divider from '@design/components/divider/Divider';
import Flex from '@design/components/flex/Flex';

import ArticleListItem from '@study-room-page/tabs/community-tab-panel/components/article-list-item/ArticleListItem';

import Pagination from '@community-tab/components/pagination/Pagination';

const ArticleList: React.FC = () => {
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
    <Flex direction="column" rowGap="20px">
      <ul>
        {articles.map(article => (
          <li key={article.id}>
            <Link to={PATH.COMMUNITY_ARTICLE(studyId, article.id)}>
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
