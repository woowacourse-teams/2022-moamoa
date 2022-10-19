import { ApiCommunityDraftArticles, useGetInfiniteCommunityDraftArticles } from '@api/community/draft-articles';

import Divider from '@shared/divider/Divider';
import PageTitle from '@shared/page-title/PageTitle';
import PageWrapper from '@shared/page-wrapper/PageWrapper';
import SectionTitle from '@shared/section-title/SectionTitle';

import DraftList from '@draft-page/components/draft-list/DraftList';

const DraftPage: React.FC = () => {
  const { data, isFetching, isError, fetchNextPage } = useGetInfiniteCommunityDraftArticles();

  const articles = data?.pages.reduce<ApiCommunityDraftArticles['get']['responseData']['articles']>(
    (acc, cur) => [...acc, ...cur.articles],
    [],
  );

  return (
    <PageWrapper space="20px">
      <PageTitle align="center">임시 저장 목록</PageTitle>
      <Divider />
      <SectionTitle>커뮤니티 게시글</SectionTitle>
      {(() => {
        if (isError) return <ErrorMessage />;
        if (!articles || (articles && articles.length === 0)) return <NoResult />;
        return <DraftList articles={articles} isContentLoading={isFetching} onContentLoad={fetchNextPage} />;
      })()}
    </PageWrapper>
  );
};

export default DraftPage;

const NoResult = () => <div>임시 저장 목록이 없습니다.</div>;

const ErrorMessage = () => <div>임시 저장 목록을 불러오지 못했습니다.</div>;
