import { Link, useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import { changeDateSeperator } from '@utils';
import tw from '@utils/tw';

import { useGetUserInformation } from '@api/member';
import { useDeleteNoticeArticle, useGetNoticeArticle } from '@api/notice';

import { BoxButton } from '@components/button';
import ButtonGroup from '@components/button-group/ButtonGroup';
import Divider from '@components/divider/Divider';
import Flex from '@components/flex/Flex';
import MarkdownRender from '@components/markdown-render/MarkdownRender';
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

  const renderModifierButtons = () => {
    if (!getUserInformationQueryResult.isSuccess || getUserInformationQueryResult.isError) return;
    if (!data?.author.username) return;
    if (data.author.username !== getUserInformationQueryResult.data.username) return;

    return (
      <ButtonGroup gap="8px" width="fit-content">
        <GoToEditPageLinkButton />
        <DeleteArticle onClick={handleDeleteArticleButtonClick} />
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
          <Flex justifyContent="space-between" columnGap="16px">
            <UserInfoItem src={author.imageUrl} name={author.username} size="md">
              <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
              <UserInfoItem.Content>{changeDateSeperator(createdDate)}</UserInfoItem.Content>
            </UserInfoItem>
            {renderModifierButtons()}
          </Flex>
          <Divider />
          <PageTitle>{title}</PageTitle>
          <div css={tw`min-h-400 pb-20`}>
            <MarkdownRender markdownContent={content} />
          </div>
          <Divider space="8px" />
          <GoToListPageLinkButton />
        </article>
      );
    }
  };

  return <div>{render()}</div>;
};

const GoToEditPageLinkButton: React.FC = () => (
  <Link to="edit">
    <BoxButton type="button" custom={{ padding: '4px 8px' }}>
      돌아가기
    </BoxButton>
  </Link>
);

type DeleteArticleProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const DeleteArticle: React.FC<DeleteArticleProps> = ({ onClick: handleClick }) => (
  <BoxButton type="button" variant="secondary" onClick={handleClick} custom={{ padding: '4px 8px' }}>
    글삭제
  </BoxButton>
);

const GoToListPageLinkButton: React.FC = () => (
  <Link to={`../${PATH.NOTICE}`}>
    <BoxButton type="button" variant="secondary" custom={{ padding: '8px' }}>
      목록보기
    </BoxButton>
  </Link>
);

export default Article;
