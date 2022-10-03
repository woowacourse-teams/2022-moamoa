import { Link, useLocation, useParams } from 'react-router-dom';

import { Theme, useTheme } from '@emotion/react';

import { PATH } from '@constants';

import { TextButton } from '@components/button';
import Divider from '@components/divider/Divider';
import Flex from '@components/flex/Flex';
import Wrapper from '@components/wrapper/Wrapper';

import ArticleList from '@community-tab/components/article-list/ArticleList';
import Article from '@community-tab/components/article/Article';
import Edit from '@community-tab/components/edit/Edit';
import Publish from '@community-tab/components/publish/Publish';

const CommunityTabPanel: React.FC = () => {
  const theme = useTheme();
  const location = useLocation();
  const { studyId: _studyId, articleId: _articleId } = useParams<{ studyId: string; articleId: string }>();
  const [studyId, articleId] = [Number(_studyId), Number(_articleId)];

  const lastPath = location.pathname.split('/').at(-1);
  const isPublishPage = lastPath === 'publish';
  const isEditPage = lastPath === 'edit';
  const isArticleDetailPage = !!(articleId && !isPublishPage && !isEditPage);
  const isListPage = !!(!articleId && !isPublishPage && !isEditPage && !isArticleDetailPage);

  const renderArticleListPage = () => {
    return (
      <>
        <Flex justifyContent="flex-end">
          <GoToPublishPageLinkButton theme={theme} />
        </Flex>
        <Divider color={theme.colors.secondary.dark} space="8px" />
        <ArticleList studyId={studyId} />
      </>
    );
  };

  const render = () => {
    if (isListPage) {
      return renderArticleListPage();
    }
    if (isArticleDetailPage) {
      return <Article studyId={studyId} articleId={articleId} />;
    }
    if (isPublishPage) {
      return <Publish studyId={studyId} />;
    }
    if (isEditPage) {
      return <Edit studyId={studyId} articleId={articleId} />;
    }
  };

  return (
    <Wrapper>
      <div>{render()}</div>
    </Wrapper>
  );
};

type GoToPublishPageLinkButtonProps = {
  theme: Theme;
};

const GoToPublishPageLinkButton: React.FC<GoToPublishPageLinkButtonProps> = ({ theme }) => (
  <Link to={PATH.COMMUNITY_PUBLISH}>
    <TextButton variant="primary" custom={{ fontSize: theme.fontSize.lg }}>
      글쓰기
    </TextButton>
  </Link>
);

export default CommunityTabPanel;
