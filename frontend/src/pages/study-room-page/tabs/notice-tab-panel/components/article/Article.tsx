import { Link, useNavigate } from 'react-router-dom';

import { css } from '@emotion/react';

import { PATH } from '@constants';

import { changeDateSeperator } from '@utils';

import { type CommunityArticle } from '@custom-types';

import { useGetUserInformation } from '@api/member';
import { useDeleteNoticeArticle, useGetNoticeArticle } from '@api/notice';

import { BoxButton } from '@components/button';
import ButtonGroup from '@components/button-group/ButtonGroup';
import Divider from '@components/divider/Divider';
import Flex from '@components/flex/Flex';
import ImportedMarkdownRender from '@components/markdown-render/MarkdownRender';
import PageTitle from '@components/page-title/PageTitle';
import UserInfoItem from '@components/user-info-item/UserInfoItem';

export type ArticleProps = {
  studyId: number;
  articleId: number;
};

const Article: React.FC<ArticleProps> = ({ studyId, articleId }) => {
  const { isFetching, isSuccess, isError, data } = useGetNoticeArticle({ studyId, articleId });
  const getUserInformationQueryResult = useGetUserInformation();

  const { mutateAsync } = useDeleteNoticeArticle();
  const navigate = useNavigate();

  const handleDeleteArticleButtonClick = () => {
    mutateAsync(
      { studyId, articleId },
      {
        onSuccess: () => {
          alert('성공적으로 삭제했습니다');
          navigate(`../${PATH.NOTICE}`);
        },
        onError: () => {
          alert('알 수 없는 에러가 발생했습니다');
        },
      },
    );
  };

  const showModifierButtons = !!(
    getUserInformationQueryResult.isSuccess &&
    !getUserInformationQueryResult.isError &&
    data?.author &&
    data.author.username === getUserInformationQueryResult.data.username
  );

  return (
    <div>
      {isFetching && <Loading />}
      {isError && <Error />}
      {isSuccess && (
        <article>
          <Flex justifyContent="space-between" columnGap="16px">
            <UserInfoItem src={data.author.imageUrl} name={data.author.username} size="md">
              <UserInfoItem.Heading>{data.author.username}</UserInfoItem.Heading>
              <UserInfoItem.Content>{changeDateSeperator(data.createdDate)}</UserInfoItem.Content>
            </UserInfoItem>
            {showModifierButtons && (
              <ButtonGroup gap="8px" custom={{ width: 'fit-content' }}>
                <GoToEditPageLinkButton />
                <DeleteArticleButton onClick={handleDeleteArticleButtonClick} />
              </ButtonGroup>
            )}
          </Flex>
          <Divider />
          <PageTitle>{data.title}</PageTitle>
          <MarkdownRender content={data.content} />
          <Divider space="8px" />
          <GoToListPageLinkButton />
        </article>
      )}
    </div>
  );
};

const Loading = () => <div>Loading...</div>;

const Error = () => <div>에러가 발생했습니다</div>;

const GoToEditPageLinkButton: React.FC = () => (
  <Link to="edit">
    <BoxButton type="button" custom={{ padding: '4px 8px' }}>
      글수정
    </BoxButton>
  </Link>
);

type DeleteArticleButtonProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const DeleteArticleButton: React.FC<DeleteArticleButtonProps> = ({ onClick: handleClick }) => (
  <BoxButton type="button" variant="secondary" onClick={handleClick} custom={{ padding: '4px 8px' }}>
    글 삭제
  </BoxButton>
);

const GoToListPageLinkButton: React.FC = () => (
  <Link to={`../${PATH.NOTICE}`}>
    <BoxButton type="button" variant="secondary" custom={{ padding: '8px' }}>
      목록보기
    </BoxButton>
  </Link>
);

const MarkdownRender: React.FC<{ content: string }> = ({ content }) => {
  const style = css`
    min-height: 400px;
    padding-bottom: 20px;
  `;
  return (
    <div css={style}>
      <ImportedMarkdownRender markdownContent={content} />
    </div>
  );
};

export default Article;
