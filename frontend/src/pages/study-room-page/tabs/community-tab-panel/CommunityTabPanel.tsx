import { Link, useLocation, useParams } from 'react-router-dom';

import { type Theme, useTheme } from '@emotion/react';

import { PATH } from '@constants';

import { type StudyId } from '@custom-types';

import { TextButton } from '@components/button';
import Divider from '@components/divider/Divider';
import Flex from '@components/flex/Flex';
import PageWrapper from '@components/page-wrapper/PageWrapper';

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

  return (
    <PageWrapper>
      {isListPage && <ArticleListPage theme={theme} studyId={studyId} />}
      {isArticleDetailPage && <Article studyId={studyId} articleId={articleId} />}
      {isPublishPage && <Publish studyId={studyId} />}
      {isEditPage && <Edit studyId={studyId} articleId={articleId} />}
    </PageWrapper>
  );
};

const GoToPublishPageLinkButton: React.FC = () => (
  <Link to={PATH.COMMUNITY_PUBLISH}>
    <TextButton variant="primary" custom={{ fontSize: 'lg' }}>
      글쓰기
    </TextButton>
  </Link>
);

type ArticleListPageProps = {
  theme: Theme;
  studyId: StudyId;
};
const ArticleListPage: React.FC<ArticleListPageProps> = ({ theme, studyId }) => (
  <>
    <Flex justifyContent="flex-end">
      <GoToPublishPageLinkButton />
    </Flex>
    <Divider color={theme.colors.secondary.dark} space="8px" />
    <ArticleList studyId={studyId} />
  </>
);

export default CommunityTabPanel;
