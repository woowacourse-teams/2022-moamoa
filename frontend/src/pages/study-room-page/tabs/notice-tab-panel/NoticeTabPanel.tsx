import { Navigate, useLocation, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { useUserRole } from '@hooks/useUserRole';

import PageWrapper from '@shared/page-wrapper/PageWrapper';

import Edit from '@notice-tab/components/article-detail-edit/NoticeArticleDetailEdit';
import ArticleDetail from '@notice-tab/components/article-detail/NoticeArticleDetail';
import ArticleListPage from '@notice-tab/components/article-list-page/NoticeArticleListPage';
import Publish from '@notice-tab/components/publish/NoticeArticleDetailPublish';

const NoticeTabPanel: React.FC = () => {
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
        if (isDetailPage) return <ArticleDetail studyId={studyId} articleId={articleId} />;
        return <ArticleListPage studyId={studyId} />;
      })()}
    </PageWrapper>
  );
};

export default NoticeTabPanel;
