import type { ArticleId, StudyId } from '@custom-types';

import { useDeleteCommunityDraftArticle } from '@api/community/draft-article';
import { type ApiCommunityDraftArticles, useGetInfiniteCommunityDraftArticles } from '@api/community/draft-articles';

import Divider from '@shared/divider/Divider';
import InfiniteScroll from '@shared/infinite-scroll/InfiniteScroll';
import PageTitle from '@shared/page-title/PageTitle';
import PageWrapper from '@shared/page-wrapper/PageWrapper';
import SectionTitle from '@shared/section-title/SectionTitle';

import DraftList from '@draft-page/components/draft-list/DraftList';

const DraftPage: React.FC = () => {
  const { data, isFetching, isError, fetchNextPage, refetch } = useGetInfiniteCommunityDraftArticles();
  const { mutate } = useDeleteCommunityDraftArticle();

  const draftArticles = data?.pages.reduce<ApiCommunityDraftArticles['get']['responseData']['draftArticles']>(
    (acc, cur) => [...acc, ...cur.draftArticles],
    [],
  );

  const handleDeleteDraftItemClick = (studyId: StudyId, articleId: ArticleId) => () => {
    mutate(
      { studyId, articleId },
      {
        onError: () => {
          alert('문제가 발생하여 삭제하지 못했습니다.');
        },
        onSuccess: () => {
          alert('삭제했습니다 :D');
          refetch();
        },
      },
    );
  };

  return (
    <PageWrapper space="40px">
      <PageTitle align="center">임시 저장 목록</PageTitle>
      <Divider />
      <SectionTitle>커뮤니티 게시글</SectionTitle>
      {(() => {
        if (isError) return <ErrorMessage />;
        if (!draftArticles || (draftArticles && draftArticles.length === 0)) return <NoResult />;
        return (
          <InfiniteScroll isContentLoading={isFetching} onContentLoad={fetchNextPage}>
            <DraftList draftArticles={draftArticles} onDeleteDraftItemClick={handleDeleteDraftItemClick} />
          </InfiniteScroll>
        );
      })()}
    </PageWrapper>
  );
};

export default DraftPage;

const NoResult = () => <div>임시 저장 목록이 없습니다.</div>;

const ErrorMessage = () => <div>임시 저장 목록을 불러오지 못했습니다.</div>;
