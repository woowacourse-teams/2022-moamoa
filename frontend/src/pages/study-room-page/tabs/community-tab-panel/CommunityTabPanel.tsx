import { AxiosError } from 'axios';
import { useState } from 'react';

import tw from '@utils/tw';

import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@study-room-page/tabs/community-tab-panel/CommunityTabPanel.style';
import ArticleList from '@study-room-page/tabs/community-tab-panel/components/article-list/ArticleList';
import Pagination from '@study-room-page/tabs/community-tab-panel/components/pagination/Pagination';
import useGetCommunityArticles from '@study-room-page/tabs/community-tab-panel/hooks/useGetCommunityAticles';

AxiosError;

type CommunityTabPanelProps = {
  studyId: number;
};

const CommunityTabPanel: React.FC<CommunityTabPanelProps> = ({ studyId }) => {
  const [page, setPage] = useState<number>(1);
  const { isFetching, isSuccess, isError, data } = useGetCommunityArticles(studyId, page);

  const renderArticleList = () => {
    if (isFetching) {
      return <div>Loading...</div>;
    }

    if (isError || !isSuccess) {
      return <div>에러가 발생했습니다</div>;
    }

    const { articles, lastPage, currentPage } = data;

    return (
      <>
        <ArticleList articles={articles} css={tw`mb-30`} />
        <Pagination
          count={lastPage}
          defaultPage={currentPage}
          onNumberButtonClick={num => {
            setPage(num);
          }}
          renderItem={() => undefined}
        />
      </>
    );
  };

  return (
    <Wrapper>
      <S.CommunityTabPanel>
        <S.Board>
          <h1 css={tw`text-center text-30 mb-20`}>커뮤니티 게시판</h1>
          <div css={tw`min-h-[300px]`}>{renderArticleList()}</div>
        </S.Board>
      </S.CommunityTabPanel>
    </Wrapper>
  );
};

export default CommunityTabPanel;
