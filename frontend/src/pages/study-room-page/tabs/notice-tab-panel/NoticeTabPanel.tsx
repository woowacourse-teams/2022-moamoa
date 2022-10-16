import { Link, Navigate, useLocation, useParams } from 'react-router-dom';

import { type Theme, useTheme } from '@emotion/react';

import { PATH } from '@constants';

import type { StudyId } from '@custom-types';

import { useUserRole } from '@hooks/useUserRole';

import { TextButton } from '@shared/button';
import Divider from '@shared/divider/Divider';
import Flex from '@shared/flex/Flex';
import PageWrapper from '@shared/page-wrapper/PageWrapper';

import ArticleList from '@notice-tab/components/article-list/ArticleList';
import Article from '@notice-tab/components/article/Article';
import Edit from '@notice-tab/components/edit/Edit';
import Publish from '@notice-tab/components/publish/Publish';

const NoticeTabPanel: React.FC = () => {
  const theme = useTheme();
  const location = useLocation();
  const { studyId: _studyId, articleId: _articleId } = useParams<{ studyId: string; articleId: string }>();
  const [studyId, articleId] = [Number(_studyId), Number(_articleId)];

  const { isOwner, isNonMember, isMember } = useUserRole({ studyId });

  const lastPath = location.pathname.split('/').at(-1);

  if (isNonMember || isMember) return <Navigate to={`../${PATH.NOTICE}`} replace />;

  return (
    <PageWrapper>
      {(() => {
        if (lastPath === 'publish') return <Publish studyId={studyId} />;
        if (articleId) {
          if (lastPath === 'edit') return <Edit studyId={studyId} articleId={articleId} />;
          return <Article studyId={studyId} articleId={articleId} />;
        }
        return <ArticleListPage theme={theme} studyId={studyId} isOwner={isOwner} />;
      })()}
    </PageWrapper>
  );
};

export default NoticeTabPanel;

type ArticleListPageProps = {
  theme: Theme;
  studyId: StudyId;
  isOwner: boolean;
};
const ArticleListPage: React.FC<ArticleListPageProps> = ({ theme, studyId }) => {
  const { isOwner } = useUserRole({ studyId }); // @TODO: 객체로 받을 필요가 있나?
  return (
    <>
      <Flex justifyContent="flex-end">{isOwner && <PublishPageLink />}</Flex>
      <Divider color={theme.colors.secondary.dark} space="8px" />
      <ArticleList studyId={studyId} />
    </>
  );
};

const PublishPageLink: React.FC = () => (
  <Link to={PATH.NOTICE_PUBLISH}>
    <TextButton variant="primary" custom={{ fontSize: 'lg' }}>
      글쓰기
    </TextButton>
  </Link>
);
