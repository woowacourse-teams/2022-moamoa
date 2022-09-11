import * as S from '@notice-tab/NoticeTabPanel.style';
import ArticleList from '@notice-tab/components/article-list/ArticleList';
import Article from '@notice-tab/components/article/Article';
import Edit from '@notice-tab/components/edit/Edit';
import Publish from '@notice-tab/components/publish/Publish';
import usePermission from '@notice-tab/hooks/usePermission';
import { useNavigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import tw from '@utils/tw';

import Wrapper from '@design/components/wrapper/Wrapper';

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
      <div css={tw`flex flex-col gap-y-40`}>
        <div css={tw`flex-1 min-h-[500px]`}>
          <ArticleList />
        </div>
        <div css={tw`flex justify-end`}>
          {isOwner && (
            <S.Button type="button" onClick={handleGoToPublishPageButtonClick}>
              글쓰기
            </S.Button>
          )}
        </div>
      </div>
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
