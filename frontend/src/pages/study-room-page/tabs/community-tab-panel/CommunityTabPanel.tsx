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
  const lastPath = location.pathname.split('/').at(-1);
  const { isNonMember } = useUserRole({ studyId });

  if (isNonMember) return <Navigate to={`../${PATH.COMMUNITY}`} replace />;

  return (
    <PageWrapper>
      {(() => {
        if (lastPath === 'publish') return <Publish studyId={studyId} />;
        if (articleId) {
          if (lastPath === 'edit') return <Edit studyId={studyId} articleId={articleId} />;
          return <Article studyId={studyId} articleId={articleId} />;
        }
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
  const { isNonMember } = useUserRole({ studyId });

  return (
    <>
      {isNonMember && (
        <Flex justifyContent="flex-end">
          <PublishPageLink />
        </Flex>
      )}
      <Divider color={theme.colors.secondary.dark} space="8px" />
      <ArticleList studyId={studyId} />
    </>
  );
};
