import { Link, useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import type { ArticleId, CommunityArticle, StudyId } from '@custom-types';

import { useGetCommunityArticle, usePutCommunityArticle } from '@api/community';

import { FormProvider, type UseFormReturn, type UseFormSubmitResult, useForm } from '@hooks/useForm';

import { BoxButton } from '@shared/button';
import ButtonGroup from '@shared/button-group/ButtonGroup';
import Divider from '@shared/divider/Divider';
import Form from '@shared/form/Form';
import PageTitle from '@shared/page-title/PageTitle';

import EditContent from '@community-tab/components/edit-content/EditContent';
import EditTitle from '@community-tab/components/edit-title/EditTitle';

export type EditProps = {
  studyId: StudyId;
  articleId: ArticleId;
};

type HandleEditFormSubmit = (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => Promise<any>;

const Edit: React.FC<EditProps> = ({ studyId, articleId }) => {
  const formMethods = useForm();
  const navigate = useNavigate();

  const { isFetching, isSuccess, isError, data } = useGetCommunityArticle({ studyId, articleId });
  const { mutateAsync } = usePutCommunityArticle();

  const handleSubmit: HandleEditFormSubmit = async (
    _: React.FormEvent<HTMLFormElement>,
    submitResult: UseFormSubmitResult,
  ) => {
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

  return (
    <FormProvider {...formMethods}>
      <PageTitle>게시글 수정</PageTitle>
      {(() => {
        if (isFetching) return <Loading />;
        if (isError) return <Error />;
        if (isSuccess) {
          return <EditForm article={data} formMethods={formMethods} onSubmit={handleSubmit} />;
        }
      })()}
    </FormProvider>
  );
};

export default Edit;

const GoBackLinkButton: React.FC = () => (
  <Link to={`../${PATH.COMMUNITY}`}>
    <BoxButton type="button" variant="secondary" custom={{ padding: '4px 8px', fontSize: 'lg' }}>
      돌아가기
    </BoxButton>
  </Link>
);

const EditButton: React.FC = () => (
  <BoxButton type="submit" fluid={false} custom={{ padding: '4px 8px', fontSize: 'lg' }}>
    수정하기
  </BoxButton>
);

const Loading = () => <div>Loading...</div>;

const Error = () => <div>Error...</div>;

type EditFormProps = {
  article: CommunityArticle;
  formMethods: UseFormReturn;
  onSubmit: HandleEditFormSubmit;
};
const EditForm: React.FC<EditFormProps> = ({ article, formMethods, onSubmit }) => (
  <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
    <EditTitle title={article.title} />
    <EditContent content={article.content} />
    <Divider space="16px" />
    <ButtonGroup justifyContent="space-between">
      <GoBackLinkButton />
      <EditButton />
    </ButtonGroup>
  </Form>
);
