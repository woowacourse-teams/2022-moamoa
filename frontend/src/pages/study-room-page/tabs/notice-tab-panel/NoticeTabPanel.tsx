import { Link, useLocation, useParams } from 'react-router-dom';

import { useTheme } from '@emotion/react';

import { PATH } from '@constants';

import { TextButton } from '@components/button';
import Divider from '@components/divider/Divider';
import Flex from '@components/flex/Flex';
import PageWrapper from '@components/page-wrapper/PageWrapper';

import ArticleList from '@notice-tab/components/article-list/ArticleList';
import Article from '@notice-tab/components/article/Article';
import Edit from '@notice-tab/components/edit/Edit';
import Publish from '@notice-tab/components/publish/Publish';
import usePermission from '@notice-tab/hooks/usePermission';

const NoticeTabPanel: React.FC = () => {
  const theme = useTheme();
  const location = useLocation();
  const { studyId: _studyId, articleId: _articleId } = useParams<{ studyId: string; articleId: string }>();
  const [studyId, articleId] = [Number(_studyId), Number(_articleId)];

  const { hasPermission: isOwner } = usePermission(studyId, 'OWNER');
  const lastPath = location.pathname.split('/').at(-1);
  const isPublishPage = lastPath === 'publish';
  const isEditPage = lastPath === 'edit';
  const isArticleDetailPage = !!(articleId && !isPublishPage && !isEditPage);
  const isListPage = !!(!articleId && !isPublishPage && !isEditPage && !isArticleDetailPage);

  const renderArticleListPage = () => {
    return (
      <>
        <Flex justifyContent="flex-end">{isOwner && <GoToPublishPageLinkButton />}</Flex>
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
      const numArticleId = Number(articleId);
      return <Article studyId={studyId} articleId={numArticleId} />;
    }
    if (isPublishPage) {
      return <Publish studyId={studyId} />;
    }
    if (isEditPage) {
      return <Edit studyId={studyId} articleId={articleId} />;
    }
  };

  return (
    <PageWrapper>
      <div>{render()}</div>
    </Wrapper>
  );
};

const GoToPublishPageLinkButton: React.FC = () => (
  <Link to={PATH.NOTICE_PUBLISH}>
    <TextButton variant="primary" custom={{ fontSize: 'lg' }}>
      글쓰기
    </TextButton>
  </Link>
);

export default NoticeTabPanel;
