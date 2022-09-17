import ArticleList from '@notice-tab/components/article-list/ArticleList';
import Article from '@notice-tab/components/article/Article';
import Edit from '@notice-tab/components/edit/Edit';
import Publish from '@notice-tab/components/publish/Publish';
import usePermission from '@notice-tab/hooks/usePermission';
import { useNavigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { theme } from '@styles/theme';

import { TextButton } from '@components/button';
import Divider from '@components/divider/Divider';
import Flex from '@components/flex/Flex';
import Wrapper from '@components/wrapper/Wrapper';

type NoticeTabPanelProps = {
  studyId: number;
};

const NoticeTabPanel: React.FC<NoticeTabPanelProps> = ({ studyId }) => {
  const { articleId } = useParams<{ articleId: string }>();
  const navigate = useNavigate();
  const { hasPermission: isOwner } = usePermission(studyId, 'OWNER');
  const lastPath = window.location.pathname.split('/').at(-1);
  const isPublishPage = lastPath === 'publish';
  const isEditPage = lastPath === 'edit';
  const isArticleDetailPage = !!(articleId && !isPublishPage && !isEditPage);
  const isListPage = !!(!articleId && !isPublishPage && !isEditPage && !isArticleDetailPage);

  const handleGoToPublishPageButtonClick = () => {
    navigate(`${PATH.NOTICE_PUBLISH(studyId)}`);
  };

  const renderArticleListPage = () => {
    return (
      <>
        <Flex justifyContent="flex-end">
          {isOwner && (
            <TextButton variant="primary" fontSize="lg" onClick={handleGoToPublishPageButtonClick}>
              글쓰기
            </TextButton>
          )}
        </Flex>
        <Divider color={theme.colors.secondary.dark} space="8px" />
        <ArticleList />
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
      return <Publish />;
    }
    if (isEditPage) {
      return <Edit />;
    }
  };

  return (
    <Wrapper>
      <div>{render()}</div>
    </Wrapper>
  );
};

export default NoticeTabPanel;
