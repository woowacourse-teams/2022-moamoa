import { Link, useNavigate, useParams } from 'react-router-dom';

import { css } from '@emotion/react';

import { PATH } from '@constants';

import { changeDateSeperator } from '@utils';

import { useDeleteCommunityArticle, useGetCommunityArticle } from '@api/community/article';

import { useUserInfo } from '@hooks/useUserInfo';

import { BoxButton } from '@shared/button';
import ButtonGroup from '@shared/button-group/ButtonGroup';
import Divider from '@shared/divider/Divider';
import Flex from '@shared/flex/Flex';
import ImportedMarkdownRender from '@shared/markdown-render/MarkdownRender';
import PageTitle from '@shared/page-title/PageTitle';
import UserInfoItem from '@shared/user-info-item/UserInfoItem';

const Article: React.FC = () => {
  const { studyId: _studyId, articleId: _articleId } = useParams<{ studyId: string; articleId: string }>();
  const [studyId, articleId] = [Number(_studyId), Number(_articleId)];

  const { isFetching, isSuccess, isError, data } = useGetCommunityArticle({ studyId, articleId });
  const { userInfo } = useUserInfo();

  const { mutate } = useDeleteCommunityArticle();
  const navigate = useNavigate();

  // TODO: 왜 mutateAsync지??
  const handleDeleteArticleButtonClick = () => {
    mutate(
      { studyId, articleId },
      {
        onSuccess: () => {
          alert('성공적으로 삭제했습니다');
          navigate(`../../${PATH.COMMUNITY}`);
        },
        onError: () => {
          alert('알 수 없는 에러가 발생했습니다');
        },
      },
    );
  };

  return (
    <div>
      {(() => {
        if (isFetching) return <Loading />;
        if (isError) return <Error />;
        if (isSuccess) {
          const isMyArticle = data.author.id === userInfo.id;
          // @TODO: react-query 버전 업데이트
          return (
            <article>
              <Flex justifyContent="space-between" columnGap="16px">
                <UserInfoItem src={data.author.imageUrl} name={data.author.username} size="md">
                  <UserInfoItem.Heading>{data.author.username}</UserInfoItem.Heading>
                  <UserInfoItem.Content>{changeDateSeperator(data.createdDate)}</UserInfoItem.Content>
                </UserInfoItem>
                {isMyArticle && (
                  <ButtonGroup gap="8px" custom={{ width: 'fit-content' }}>
                    <EditArticleLink articleId={articleId} />
                    <DeleteArticleButton onClick={handleDeleteArticleButtonClick} />
                  </ButtonGroup>
                )}
              </Flex>
              <Divider />
              <PageTitle>{data.title}</PageTitle>
              <MarkdownRender content={data.content} />
              <Divider space="8px" />
              <ListPageLink />
            </article>
          );
        }
      })()}
    </div>
  );
};

export default Article;

const Loading = () => <div>Loading...</div>;

const Error = () => <div>에러가 발생했습니다</div>;

type EditArticleLinkProps = {
  articleId: number;
};
const EditArticleLink: React.FC<EditArticleLinkProps> = ({ articleId }) => (
  <Link to={`../${PATH.COMMUNITY_EDIT(articleId)}`}>
    <BoxButton type="button" custom={{ padding: '4px 8px' }}>
      글 수정
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

const ListPageLink: React.FC = () => (
  <Link to={`../../${PATH.COMMUNITY}`}>
    <BoxButton type="button" variant="secondary" custom={{ padding: '8px' }}>
      목록보기
    </BoxButton>
  </Link>
);

const MarkdownRender: React.FC<{ content: string }> = ({ content }) => {
  const style = css`
    min-height: 400px;
    overflow: auto;
    padding-bottom: 20px;
  `;
  return (
    <div css={style}>
      <ImportedMarkdownRender markdownContent={content} />
    </div>
  );
};
