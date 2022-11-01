import { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import type { ArticleId, NoticeArticleDetail, StudyId } from '@custom-types';

import { useGetNoticeArticleDetail, usePutNoticeArticleDetail } from '@api/notice/article-detail';

import { FormProvider, type UseFormReturn, type UseFormSubmitResult, useForm } from '@hooks/useForm';
import { useUserRole } from '@hooks/useUserRole';

import { BoxButton } from '@shared/button';
import ButtonGroup from '@shared/button-group/ButtonGroup';
import Divider from '@shared/divider/Divider';
import Form from '@shared/form/Form';
import PageTitle from '@shared/page-title/PageTitle';

import ArticleContentInput from '@components/article-content-input/ArticleContentInput';
import ArticleTitleInput from '@components/article-title-input/ArticleTitleInput';

export type NoticeArticleDetailEditProps = {
  studyId: StudyId;
  articleId: ArticleId;
};

type HandleEditFormSubmit = (
  _: React.FormEvent<HTMLFormElement>,
  submitResult: UseFormSubmitResult,
) => Promise<null | undefined>;

const NoticeArticleDetailEdit: React.FC<NoticeArticleDetailEditProps> = ({ studyId, articleId }) => {
  const formMethods = useForm();
  const navigate = useNavigate();

  const getNoticeArticleQueryResult = useGetNoticeArticleDetail({ studyId, articleId });
  const { mutateAsync } = usePutNoticeArticleDetail();
  const { isFetching, isOwner } = useUserRole({ studyId });

  useEffect(() => {
    if (isFetching) return;
    if (isOwner) return;

    alert('접근할 수 없습니다!');
    navigate(`../${PATH.NOTICE}`);
  }, [studyId, navigate, isFetching, isOwner]);

  const handleSubmit: HandleEditFormSubmit = async (_, submitResult) => {
    const { values } = submitResult;
    if (!values) return;

    const { title, content } = values;

    return mutateAsync(
      {
        studyId,
        articleId,
        title,
        content,
      },
      {
        onSuccess: () => {
          alert('글을 수정했습니다 :D');
          navigate(`../${PATH.NOTICE}`, { replace: true });
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
      {(() => {
        const { isFetching, isError, isSuccess, data } = getNoticeArticleQueryResult;
        if (isFetching) return <Loading />;
        if (isError) return <Error />;
        if (isSuccess) return <EditForm article={data} formMethods={formMethods} onSubmit={handleSubmit} />;
      })()}
    </FormProvider>
  );
};

export default NoticeArticleDetailEdit;

type EditFormProps = {
  article: NoticeArticleDetail;
  formMethods: UseFormReturn;
  onSubmit: HandleEditFormSubmit;
};
const EditForm: React.FC<EditFormProps> = ({ article, formMethods, onSubmit }) => (
  <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
    <ArticleTitleInput originalTitle={article.title} />
    <ArticleContentInput originalContent={article.content} />
    <Divider space="16px" />
    <ButtonGroup justifyContent="space-between">
      <ListPageLink />
      <EditButton />
    </ButtonGroup>
  </Form>
);

const ListPageLink: React.FC = () => (
  <Link to={`../${PATH.NOTICE}`}>
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
