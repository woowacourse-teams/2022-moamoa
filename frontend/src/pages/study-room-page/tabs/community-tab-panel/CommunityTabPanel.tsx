import { Link, Navigate, useLocation, useParams } from 'react-router-dom';

import { type Theme, useTheme } from '@emotion/react';

import { PATH } from '@constants';

import type { StudyId } from '@custom-types';

import { useUserRole } from '@hooks/useUserRole';

import { TextButton } from '@shared/button';
import Divider from '@shared/divider/Divider';
import Flex from '@shared/flex/Flex';
import PageWrapper from '@shared/page-wrapper/PageWrapper';

import ArticleList from '@community-tab/components/article-list/ArticleList';
import Article from '@community-tab/components/article/Article';
import Edit from '@community-tab/components/edit/Edit';
import Publish from '@community-tab/components/publish/Publish';

const CommunityTabPanel: React.FC = () => {
  const theme = useTheme();
  const location = useLocation();
  const { studyId: _studyId, articleId: _articleId } = useParams<{ studyId: string; articleId: string }>();
  const [studyId, articleId] = [Number(_studyId), Number(_articleId)];

  // @TODO: studyId가 없는 경우는 어떻게 처리할것인가?
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
        return <ArticleListPage theme={theme} studyId={studyId} />;
      })()}
    </PageWrapper>
  );
};

export default CommunityTabPanel;

const PublishPageLink: React.FC = () => (
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
const ArticleListPage: React.FC<ArticleListPageProps> = ({ theme, studyId }) => {
  const { isOwnerOrMember } = useUserRole({ studyId });

  return (
    <>
      {isOwnerOrMember && (
        <Flex justifyContent="flex-end">
          <PublishPageLink />
        </Flex>
      )}
      <Divider color={theme.colors.secondary.dark} space="8px" />
      <ArticleList studyId={studyId} />
    </>
  );
};
