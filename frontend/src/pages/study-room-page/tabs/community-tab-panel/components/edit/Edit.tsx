import { Link, useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import type { ArticleId, StudyId } from '@custom-types';

import { useGetCommunityArticle, usePutCommunityArticle } from '@api/community';

import { FormProvider, UseFormSubmitResult, useForm } from '@hooks/useForm';

import { BoxButton } from '@components/button';
import ButtonGroup from '@components/button-group/ButtonGroup';
import Divider from '@components/divider/Divider';
import Form from '@components/form/Form';
import PageTitle from '@components/page-title/PageTitle';

import EditContent from '@community-tab/components/edit-content/EditContent';
import EditTitle from '@community-tab/components/edit-title/EditTitle';

export type EditProps = {
  studyId: StudyId;
  articleId: ArticleId;
};

const Edit: React.FC<EditProps> = ({ studyId, articleId }) => {
  const formMethods = useForm();
  const navigate = useNavigate();

  const getCommunityArticleQueryResult = useGetCommunityArticle({ studyId, articleId });
  const { mutateAsync } = usePutCommunityArticle();

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
          alert('글을 수정했습니다 :D');
          navigate(`../${PATH.COMMUNITY}`);
        },
        onError: () => {
          alert('글을 수정하지 못했습니다. 다시 시도해주세요 :(');
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
            <Link to={`../${PATH.COMMUNITY}`}>
              <BoxButton type="button" variant="secondary" padding="4px 8px" fluid={false} fontSize="lg">
                돌아가기
              </BoxButton>
            </Link>
            <BoxButton type="submit" padding="4px 8px" fontSize="lg" fluid={false}>
              수정하기
            </BoxButton>
          </ButtonGroup>
        </Form>
      );
    }
  };

  return (
    <FormProvider {...formMethods}>
      <PageTitle>게시글 수정</PageTitle>
      {render()}
    </FormProvider>
  );
};

export default Edit;
