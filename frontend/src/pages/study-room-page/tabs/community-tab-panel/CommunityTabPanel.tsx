import { useNavigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import tw from '@utils/tw';

import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@community-tab/CommunityTabPanel.style';
import ArticleList from '@community-tab/components/article-list/ArticleList';
import Article from '@community-tab/components/article/Article';
import Edit from '@community-tab/components/edit/Edit';
import Publish from '@community-tab/components/publish/Publish';

type CommunityTabPanelProps = {
  studyId: number;
};

const CommunityTabPanel: React.FC<CommunityTabPanelProps> = ({ studyId }) => {
  const { articleId } = useParams<{ articleId: string }>();
  const navigate = useNavigate();

  const lastPath = window.location.pathname.split('/').at(-1);
  const isPublishPage = lastPath === 'publish';
  const isEditPage = lastPath === 'edit';
  const isArticleDetailPage = !!(articleId && !isPublishPage && !isEditPage);
  const isListPage = !!(!articleId && !isPublishPage && !isEditPage && !isArticleDetailPage);

  const handleGoToPublishPageButtonClick = () => {
    navigate(`${PATH.COMMUNITY_PUBLISH(studyId)}`);
  };

  const renderArticleListPage = () => {
    return (
      <div css={tw`flex flex-col gap-y-40`}>
        <div css={tw`flex-1 min-h-[500px]`}>
          <ArticleList />
        </div>
        <div css={tw`flex justify-end`}>
          <S.Button type="button" onClick={handleGoToPublishPageButtonClick}>
            글쓰기
          </S.Button>
        </div>
      </div>
    );
  };

  const render = () => {
    if (isListPage) {
      return renderArticleListPage();
    }
    if (isArticleDetailPage) {
      const numArticleId = Number(articleId);
      return <Article studyId={studyId} articleId={numArticleId} />;
    }
    if (isPublishPage) {
      return <Publish />;
    }
    if (isEditPage) {
      return <Edit />;
    }
  };

  return (
    <Wrapper>
      <div>
        <h1 css={tw`text-center text-30 mb-40`}>커뮤니티</h1>
        <div>{render()}</div>
      </div>
    </Wrapper>
  );
};

export default CommunityTabPanel;
