import { FC } from 'react';
import { useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import tw from '@utils/tw';

import { useGetUserInformation } from '@api/member';
import { useDeleteNoticeArticle, useGetNoticeArticle } from '@api/notice';

import { useAuth } from '@hooks/useAuth';

import Avatar from '@components/avatar/Avatar';

import MarkdownRender from '@design/components/markdown-render/MarkdownRender';

import * as S from '@study-room-page/tabs/notice-tab-panel/components/article/Article.style';

export type ArticleProps = {
  studyId: number;
  articleId: number;
};

const Article: FC<ArticleProps> = ({ studyId, articleId }) => {
  const { isFetching, isSuccess, isError, data } = useGetNoticeArticle(studyId, articleId);
  const getUserInformationQueryResult = useGetUserInformation();

  const { mutateAsync } = useDeleteNoticeArticle();
  const navigate = useNavigate();
  const {} = useAuth();
  const handleBackToArticleListButtonClick = () => {
    navigate(`${PATH.COMMUNITY(studyId)}`);
  };

  const handleDeleteArticleButtonClick = () => {
    mutateAsync(
      { studyId, articleId },
      {
        onSuccess: () => {
          alert('성공적으로 삭제했습니다');
          navigate(`${PATH.COMMUNITY(studyId)}`);
        },
        onError: () => {
          alert('알수 없는 에러가 발생했습니다');
        },
      },
    );
  };

  const handleEditArticleButtonClick = () => {
    navigate(`${PATH.COMMUNITY_EDIT(studyId, articleId)}`);
  };

  const renderModifierButtons = () => {
    if (!getUserInformationQueryResult.isSuccess || getUserInformationQueryResult.isError) return;
    if (!data?.author.username) return;
    if (data.author.username !== getUserInformationQueryResult.data.username) return;

    return (
      <div css={tw`flex gap-x-10`}>
        <S.Button type="button" onClick={handleEditArticleButtonClick}>
          글수정
        </S.Button>
        <S.Button variant="danger" type="button" onClick={handleDeleteArticleButtonClick}>
          글삭제
        </S.Button>
      </div>
    );
  };

  const render = () => {
    if (isFetching) {
      return <div>Loading...</div>;
    }
    if (isError) {
      return <div>에러가 발생했습니다</div>;
    }

    if (isSuccess) {
      const { title, author, content, createdDate } = data;
      return (
        <div>
          <S.Header>
            <div>
              <S.Button type="button" onClick={handleBackToArticleListButtonClick}>
                목록보기
              </S.Button>
            </div>
            {renderModifierButtons()}
          </S.Header>
          <S.Main>
            <S.Title>{title}</S.Title>
            <S.Content>
              <MarkdownRender markdownContent={content} />
            </S.Content>
          </S.Main>
          <S.Footer>
            <S.Author>
              <Avatar size={'xs'} profileImg={author.imageUrl} profileAlt={`${author.username} profile`} />
              <span>{author.username}</span>
            </S.Author>
            <S.CreatedAt>{createdDate}</S.CreatedAt>
          </S.Footer>
        </div>
      );
    }
  };

  return <div>{render()}</div>;
};

export default Article;
