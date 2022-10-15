import { Link, Navigate, useLocation, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { theme } from '@styles/theme';

import { useUserRole } from '@hooks/useUserRole';

import { TextButton } from '@components/button';
import Divider from '@components/divider/Divider';
import Flex from '@components/flex/Flex';
import Wrapper from '@components/wrapper/Wrapper';

import ArticleList from '@community-tab/components/article-list/ArticleList';
import Article from '@community-tab/components/article/Article';
import Edit from '@community-tab/components/edit/Edit';
import Publish from '@community-tab/components/publish/Publish';

const CommunityTabPanel: React.FC = () => {
  const location = useLocation();
  const { studyId: _studyId, articleId: _articleId } = useParams<{ studyId: string; articleId: string }>();
  const [studyId, articleId] = [Number(_studyId), Number(_articleId)];

  const { isNonMember, isOwnerOrMember } = useUserRole({ studyId });

  const lastPath = location.pathname.split('/').at(-1);
  const isPublishPage = lastPath === 'publish';
  const isEditPage = lastPath === 'edit';
  const isArticleDetailPage = !!(articleId && !isPublishPage && !isEditPage);
  const isListPage = !!(!articleId && !isPublishPage && !isEditPage && !isArticleDetailPage);

  const renderArticleListPage = () => {
    return (
      <>
        {isOwnerOrMember && (
          <Flex justifyContent="flex-end">
            <Link to={PATH.COMMUNITY_PUBLISH}>
              <TextButton variant="primary" fontSize="lg">
                글쓰기
              </TextButton>
            </Link>
          </Flex>
        )}
        <Divider color={theme.colors.secondary.dark} space="8px" />
        <ArticleList studyId={studyId} />
      </>
    );
  };

  const render = () => {
    if (isListPage) return renderArticleListPage();

    if (isArticleDetailPage) return <Article studyId={studyId} articleId={articleId} />;

    if (isNonMember) return <Navigate to={`../${PATH.COMMUNITY}`} replace />;

    if (isPublishPage) return <Publish studyId={studyId} />;

    if (isEditPage) return <Edit studyId={studyId} articleId={articleId} />;
  };

  return (
    <Wrapper>
      <div>{render()}</div>
    </Wrapper>
  );
};

export default CommunityTabPanel;
