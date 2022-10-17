import { Navigate, useLocation, useParams } from 'react-router-dom';

import { useTheme } from '@emotion/react';

import { PATH } from '@constants';

import { useUserRole } from '@hooks/useUserRole';

import PageWrapper from '@shared/page-wrapper/PageWrapper';

import ArticleListPage from '@study-room-page/tabs/notice-tab-panel/components/article-list-page/ArticleListPage';

import Article from '@notice-tab/components/article/Article';
import Edit from '@notice-tab/components/edit/Edit';
import Publish from '@notice-tab/components/publish/Publish';

const NoticeTabPanel: React.FC = () => {
  const theme = useTheme();
  const location = useLocation();
  const { studyId: _studyId, articleId: _articleId } = useParams<{ studyId: string; articleId: string }>();
  const [studyId, articleId] = [Number(_studyId), Number(_articleId)];

  const { isOwner } = useUserRole({ studyId });

  // @TODO: Router를 활용해서 내부 페이지를 분리하자
  const lastPath = location.pathname.split('/').at(-1);
  const isPublishPage = lastPath === 'publish';
  const isEditPage = lastPath === 'edit';
  const isDetailPage = !!(articleId && !isPublishPage && !isEditPage);

  return (
    <PageWrapper>
      {(() => {
        if ((isPublishPage || isEditPage) && !isOwner) return <Navigate to={`../${PATH.NOTICE}`} replace />;
        if (isPublishPage && isOwner) return <Publish studyId={studyId} />;
        if (isEditPage && isOwner) return <Edit studyId={studyId} articleId={articleId} />;
        if (isDetailPage) return <Article studyId={studyId} articleId={articleId} />;
        return <ArticleListPage theme={theme} studyId={studyId} />;
      })()}
    </PageWrapper>
  );
};

export default NoticeTabPanel;
