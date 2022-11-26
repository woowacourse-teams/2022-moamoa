import { Fragment, useState } from 'react';

import { css } from '@emotion/react';

import type { StudyId } from '@custom-types';

import { type ApiNoticeArticleListItems, useGetNoticeArticleListItems } from '@api/notice/article-list-items';

import Divider from '@shared/divider/Divider';
import Flex from '@shared/flex/Flex';
import Pagination from '@shared/pagination/Pagination';

import ArticleListItem from '@notice-tab/components/article-list-item/NoticeArticleListItem';

export type NoticeArticleListProps = {
  studyId: StudyId;
};

const NoticeArticleList: React.FC<NoticeArticleListProps> = ({ studyId }) => {
  const [page, setPage] = useState<number>(1);
  const { isFetching, isSuccess, isError, data } = useGetNoticeArticleListItems({ studyId, page });

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
              count={data.lastPage}
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

type SelfProps = {
  articles: ApiNoticeArticleListItems['get']['responseData']['articles'];
};
const Self: React.FC<SelfProps> = ({ articles }) => (
  <ul
    css={css`
      width: 100%;
    `}
  >
    {articles.map(article => (
      <Fragment key={article.id}>
        <ArticleListItem
          id={article.id}
          title={article.title}
          author={article.author}
          createdDate={article.createdDate}
        />
        <Divider />
      </Fragment>
    ))}
  </ul>
);

export default NoticeArticleList;

const Loading = () => <div>Loading...</div>;

const Error = () => <div>에러가 발생했습니다</div>;

const NoArticle = () => <div>게시글이 없습니다</div>;
