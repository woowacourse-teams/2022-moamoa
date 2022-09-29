import { useNavigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { theme } from '@styles/theme';

import { TextButton } from '@components/button';
import Divider from '@components/divider/Divider';
import Flex from '@components/flex/Flex';
import Wrapper from '@components/wrapper/Wrapper';

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
      <>
        <Flex justifyContent="flex-end">
          <TextButton variant="primary" fontSize="lg" onClick={handleGoToPublishPageButtonClick}>
            글쓰기
          </TextButton>
        </Flex>
        <Divider color={theme.colors.secondary.dark} space="8px" />
        <ArticleList />
      </>
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
      <div>{render()}</div>
    </Wrapper>
  );
};

export default CommunityTabPanel;
