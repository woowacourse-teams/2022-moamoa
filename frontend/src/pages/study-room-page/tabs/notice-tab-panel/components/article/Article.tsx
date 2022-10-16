import { Link, useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import { changeDateSeperator } from '@utils';
import tw from '@utils/tw';

import { useDeleteNoticeArticle, useGetNoticeArticle } from '@api/notice';

import { useUserInfo } from '@hooks/useUserInfo';

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
  const { userInfo } = useUserInfo();

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

  const render = () => {
    if (isFetching) {
      return <div>Loading...</div>;
    }
    if (isError) {
      return <div>에러가 발생했습니다</div>;
    }

    if (isSuccess) {
      const { title, author, content, createdDate } = data;
      const isMyArticle = author.id === userInfo.id;

      return (
        <article>
          <Flex justifyContent="space-between" gap="16px">
            <UserInfoItem src={author.imageUrl} name={author.username} size="md">
              <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
              <UserInfoItem.Content>{changeDateSeperator(createdDate)}</UserInfoItem.Content>
            </UserInfoItem>
            {isMyArticle && (
              <ButtonGroup gap="8px" width="fit-content">
                <Link to="edit" relative="path">
                  <BoxButton type="button" padding="4px 8px" fluid={false}>
                    글수정
                  </BoxButton>
                </Link>
                <BoxButton
                  type="button"
                  padding="4px 8px"
                  fluid={false}
                  variant="secondary"
                  onClick={handleDeleteArticleButtonClick}
                >
                  글삭제
                </BoxButton>
              </ButtonGroup>
            )}
          </Flex>
          <Divider />
          <PageTitle>{title}</PageTitle>
          <div css={tw`min-h-400 pb-20`}>
            <MarkdownRender markdownContent={content} />
          </div>
          <Divider space="8px" />
          <Link to={`../${PATH.NOTICE}`}>
            <BoxButton type="button" padding="8px" variant="secondary">
              목록보기
            </BoxButton>
          </Link>
        </article>
      );
    }
  };

  return <div>{render()}</div>;
};

export default Article;
