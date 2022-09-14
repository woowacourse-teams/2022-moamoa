import { FC } from 'react';
import { useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import { changeDateSeperator } from '@utils';
import tw from '@utils/tw';

import { useDeleteCommunityArticle, useGetCommunityArticle } from '@api/community';
import { useGetUserInformation } from '@api/member';

import { useAuth } from '@hooks/useAuth';

import { BoxButton } from '@design/components/button';
import ButtonGroup from '@design/components/button-group/ButtonGroup';
import Divider from '@design/components/divider/Divider';
import Flex from '@design/components/flex/Flex';
import Item from '@design/components/item/Item';
import MarkdownRender from '@design/components/markdown-render/MarkdownRender';
import Title from '@design/components/title/Title';

export type ArticleProps = {
  studyId: number;
  articleId: number;
};

const Article: FC<ArticleProps> = ({ studyId, articleId }) => {
  const { isFetching, isSuccess, isError, data } = useGetCommunityArticle(studyId, articleId);
  const getUserInformationQueryResult = useGetUserInformation();

  const { mutateAsync } = useDeleteCommunityArticle();
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
      <ButtonGroup gap="8px" width="fit-content">
        <BoxButton type="button" padding="4px 8px" fluid={false} fontSize="md" onClick={handleEditArticleButtonClick}>
          글수정
        </BoxButton>
        <BoxButton
          type="button"
          padding="4px 8px"
          fontSize="md"
          fluid={false}
          variant="secondary"
          onClick={handleDeleteArticleButtonClick}
        >
          글삭제
        </BoxButton>
      </ButtonGroup>
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
        <article>
          <Flex justifyContent="space-between" gap="16px">
            <Item src={author.imageUrl} name={author.username} size="md">
              <Item.Heading>{author.username}</Item.Heading>
              <Item.Content>{changeDateSeperator(createdDate)}</Item.Content>
            </Item>
            {renderModifierButtons()}
          </Flex>
          <Divider />
          <Title.Page>{title}</Title.Page>
          <div css={tw`min-h-400 pb-20`}>
            <MarkdownRender markdownContent={content} />
          </div>
          <Divider space="8px" />
          <BoxButton
            type="button"
            padding="8px"
            fontSize="md"
            variant="secondary"
            onClick={handleBackToArticleListButtonClick}
          >
            목록보기
          </BoxButton>
        </article>
      );
    }
  };

  return <div>{render()}</div>;
};

export default Article;
