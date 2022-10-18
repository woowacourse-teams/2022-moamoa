import { Navigate, useLocation, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { useUserRole } from '@hooks/useUserRole';

import PageWrapper from '@shared/page-wrapper/PageWrapper';

import ArticleListPage from '@community-tab/components/article-list-page/ArticleListPage';
import Article from '@community-tab/components/article/Article';
import Edit from '@community-tab/components/edit/Edit';
import Publish from '@community-tab/components/publish/Publish';

const CommunityTabPanel: React.FC = () => {
  const location = useLocation();
  const { studyId: _studyId, articleId: _articleId } = useParams<{ studyId: string; articleId: string }>();
  const [studyId, articleId] = [Number(_studyId), Number(_articleId)];

  // @TODO: Router를 활용해서 내부 페이지를 분리하자
  const lastPath = location.pathname.split('/').at(-1);
  const isPublishPage = lastPath === 'publish';
  const isEditPage = lastPath === 'edit';
  const isDetailPage = !!(articleId && !isPublishPage && !isEditPage);
  const { isNonMember, isOwnerOrMember } = useUserRole({ studyId });

  return (
    <PageWrapper>
      {(() => {
        if ((isPublishPage || isEditPage) && isNonMember) return <Navigate to={`../${PATH.COMMUNITY}`} replace />;
        if (isPublishPage && isOwnerOrMember) return <Publish studyId={studyId} />;
        if (isEditPage && isOwnerOrMember) return <Edit studyId={studyId} articleId={articleId} />;
        if (isDetailPage) return <Article studyId={studyId} articleId={articleId} />;
        return <ArticleListPage studyId={studyId} />;
      })()}
    </PageWrapper>
  );
};

export default CommunityTabPanel;
