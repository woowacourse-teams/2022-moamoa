import { Link, useNavigate } from 'react-router-dom';

import { Theme, useTheme } from '@emotion/react';

import { PATH } from '@constants';

import type { ArticleId, CommunityArticle, StudyId } from '@custom-types';

import { useGetCommunityArticle, usePutCommunityArticle } from '@api/community';

import { FormProvider, UseFormReturn, UseFormSubmitResult, useForm } from '@hooks/useForm';

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

type HandleEditFormSubmit = (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => Promise<any>;

const Edit: React.FC<EditProps> = ({ studyId, articleId }) => {
  const theme = useTheme();
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
      {isFetching && <Loading />}
      {isError && <Error />}
      {isSuccess && data && <EditForm theme={theme} article={data} formMethods={formMethods} onSubmit={handleSubmit} />}
    </FormProvider>
  );
};

type GoBackLinkButtonProps = {
  theme: Theme;
};
const GoBackLinkButton: React.FC<GoBackLinkButtonProps> = ({ theme }) => (
  <Link to={`../${PATH.COMMUNITY}`}>
    <BoxButton type="button" variant="secondary" custom={{ padding: '4px 8px', fontSize: theme.fontSize.lg }}>
      돌아가기
    </BoxButton>
  </Link>
);

type EditButtonProps = {
  theme: Theme;
};
const EditButton: React.FC<EditButtonProps> = ({ theme }) => (
  <BoxButton type="submit" custom={{ padding: '4px 8px', fontSize: theme.fontSize.lg }}>
    수정하기
  </BoxButton>
);

const Loading = () => <div>Loading...</div>;

const Error = () => <div>Error...</div>;

type EditFormProps = {
  theme: Theme;
  article: CommunityArticle;
  formMethods: UseFormReturn;
  onSubmit: HandleEditFormSubmit;
};
const EditForm: React.FC<EditFormProps> = ({ theme, article, formMethods, onSubmit }) => (
  <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
    <EditTitle title={article.title} />
    <EditContent content={article.content} />
    <Divider space="16px" />
    <ButtonGroup justifyContent="space-between">
      <GoBackLinkButton theme={theme} />
      <EditButton theme={theme} />
    </ButtonGroup>
  </Form>
);

export default Edit;
