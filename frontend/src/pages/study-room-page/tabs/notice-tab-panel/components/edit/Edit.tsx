import { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import { useTheme } from '@emotion/react';

import { PATH } from '@constants';

import type { ArticleId, NoticeArticle, StudyId } from '@custom-types';

import { useGetNoticeArticle, usePutNoticeArticle } from '@api/notice';

import { FormProvider, UseFormReturn, UseFormSubmitResult, useForm } from '@hooks/useForm';

import { BoxButton } from '@components/button';
import ButtonGroup from '@components/button-group/ButtonGroup';
import Divider from '@components/divider/Divider';
import Form from '@components/form/Form';
import PageTitle from '@components/page-title/PageTitle';

import EditContent from '@notice-tab/components/edit-content/EditContent';
import EditTitle from '@notice-tab/components/edit-title/EditTitle';
import usePermission from '@notice-tab/hooks/usePermission';

export type EditProps = {
  studyId: StudyId;
  articleId: ArticleId;
};

type HandleEditFormSubmit = (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => Promise<any>;

const Edit: React.FC<EditProps> = ({ studyId, articleId }) => {
  const formMethods = useForm();
  const navigate = useNavigate();

  const getNoticeArticleQueryResult = useGetNoticeArticle({ studyId, articleId });
  const { mutateAsync } = usePutNoticeArticle();
  const { isFetching, hasPermission } = usePermission(studyId, 'OWNER');

  useEffect(() => {
    if (isFetching) return;
    if (hasPermission) return;

    alert('접근할 수 없습니다!');
    navigate(`../${PATH.NOTICE}`);
  }, [studyId, navigate, isFetching, hasPermission]);

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
          navigate(`../${PATH.NOTICE}`);
        },
        onError: () => {
          alert('글을 수정하지 못했습니다. 다시 시도해주세요 :(');
        },
      },
    );
  };

  return (
    <FormProvider {...formMethods}>
      <PageTitle>공지사항 수정</PageTitle>
      {getNoticeArticleQueryResult.isFetching && <Loading />}
      {getNoticeArticleQueryResult.isError && <Error />}
      {getNoticeArticleQueryResult.isSuccess && getNoticeArticleQueryResult.data && (
        <EditForm article={getNoticeArticleQueryResult.data} formMethods={formMethods} onSubmit={handleSubmit} />
      )}
    </FormProvider>
  );
};

const GoToListPageButton: React.FC = () => (
  <Link to={`../${PATH.NOTICE}`}>
    <BoxButton type="button" variant="secondary" custom={{ padding: '4px 8px', fontSize: 'lg' }}>
      돌아가기
    </BoxButton>
  </Link>
);

const EditButton: React.FC = () => (
  <BoxButton type="submit" custom={{ padding: '4px 8px', fontSize: 'lg' }}>
    수정하기
  </BoxButton>
);

const Loading = () => <div>Loading...</div>;

const Error = () => <div>Error...</div>;

type EditFormProps = {
  article: NoticeArticle;
  formMethods: UseFormReturn;
  onSubmit: HandleEditFormSubmit;
};
const EditForm: React.FC<EditFormProps> = ({ article, formMethods, onSubmit }) => (
  <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
    <EditTitle title={article.title} />
    <EditContent content={article.content} />
    <Divider space="16px" />
    <ButtonGroup justifyContent="space-between">
      <GoToListPageButton />
      <EditButton />
    </ButtonGroup>
  </Form>
);

export default Edit;
