import * as S from '@notice-tab/NoticeTabPanel.style';
import ArticleList from '@notice-tab/components/article-list/ArticleList';
import Article from '@notice-tab/components/article/Article';
import Edit from '@notice-tab/components/edit/Edit';
import Publish from '@notice-tab/components/publish/Publish';
import { useNavigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import tw from '@utils/tw';

import Wrapper from '@components/wrapper/Wrapper';

type NoticeTabPanelProps = {
  studyId: number;
};

const NoticeTabPanel: React.FC<NoticeTabPanelProps> = ({ studyId }) => {
  const { articleId } = useParams<{ articleId: string }>();
  const navigate = useNavigate();

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
      <div css={tw`flex flex-col h-full`}>
        <ArticleList css={tw`flex-1`} />
        <div css={tw`flex justify-end`}>
          <S.Button type="button" onClick={handleGoToPublishPageButtonClick}>
            글쓰기
          </S.Button>
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
    <Wrapper css={tw`h-full`}>
      <div css={tw`h-full`}>
        <h1 css={tw`text-center text-30 mb-40`}>공지사항</h1>
        <div css={tw`h-full pb-40`}>{render()}</div>
      </div>
    </Wrapper>
  );
};

export default NoticeTabPanel;
