import { useNavigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { useGetCommunityArticle, usePutCommunityArticle } from '@api/community';

import { FormProvider, UseFormSubmitResult, useForm } from '@hooks/useForm';

import { BoxButton } from '@design/components/button';
import ButtonGroup from '@design/components/button-group/ButtonGroup';
import Divider from '@design/components/divider/Divider';
import Form from '@design/components/form/Form';
import Title from '@design/components/title/Title';

import EditContent from '@community-tab/components/edit-content/EditContent';
import EditTitle from '@community-tab/components/edit-title/EditTitle';

const Edit = () => {
  const formMethods = useForm();
  const navigate = useNavigate();
  const { studyId, articleId } = useParams<{ studyId: string; articleId: string }>();
  const numStudyId = Number(studyId);
  const numArticleId = Number(articleId);

  const getCommunityArticleQueryResult = useGetCommunityArticle(numStudyId, numArticleId);
  const { mutateAsync } = usePutCommunityArticle();

  const handleGoToArticlePageButtonClick = () => {
    navigate(`${PATH.COMMUNITY_ARTICLE(studyId, articleId)}`);
  };

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    const { values } = submitResult;
    if (!values) return;

    const { title, content } = values;

    const numStudyId = Number(studyId);
    const numArticleId = Number(articleId);
    mutateAsync(
      {
        studyId: numStudyId,
        articleId: numArticleId,
        title,
        content,
      },
      {
        onSuccess: () => {
          alert('글 수정 완료!');
          navigate(PATH.COMMUNITY(numStudyId));
        },
        onError: () => {
          alert('글 수정 실패!');
        },
      },
    );
  };

  const render = () => {
    if (getCommunityArticleQueryResult.isFetching) {
      return <div>Loading...</div>;
    }

    if (getCommunityArticleQueryResult.isError) {
      return <div>Error...</div>;
    }

    if (getCommunityArticleQueryResult.isSuccess) {
      return (
        <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
          <EditTitle title={getCommunityArticleQueryResult.data.title} />
          <EditContent content={getCommunityArticleQueryResult.data.content} />
          <Divider space="16px" />
          <ButtonGroup justifyContent="space-between">
            <li>
              <BoxButton
                type="button"
                variant="secondary"
                padding="4px 8px"
                fluid={false}
                onClick={handleGoToArticlePageButtonClick}
              >
                돌아가기
              </BoxButton>
            </li>
            <li>
              <BoxButton type="submit" padding="4px 8px" fluid={false}>
                수정하기
              </BoxButton>
            </li>
          </ButtonGroup>
        </Form>
      );
    }
  };

  return (
    <FormProvider {...formMethods}>
      <Title.Page>게시글 수정</Title.Page>
      {render()}
    </FormProvider>
  );
};

export default Edit;
