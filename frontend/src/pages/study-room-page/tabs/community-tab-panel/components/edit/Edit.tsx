import { Link, useNavigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import type { CommunityArticle } from '@custom-types';

import { useGetCommunityArticle, usePutCommunityArticle } from '@api/community/article';

import { FormProvider, type UseFormReturn, type UseFormSubmitResult, useForm } from '@hooks/useForm';

import { BoxButton } from '@shared/button';
import ButtonGroup from '@shared/button-group/ButtonGroup';
import Divider from '@shared/divider/Divider';
import Form from '@shared/form/Form';
import PageTitle from '@shared/page-title/PageTitle';

import ArticleContentInput from '@components/article-content-input/ArticleContentInput';
import ArticleTitleInput from '@components/article-title-input/ArticleTitleInput';

type HandleEditFormSubmit = (
  _: React.FormEvent<HTMLFormElement>,
  submitResult: UseFormSubmitResult,
) => Promise<null | undefined>;

const Edit: React.FC = () => {
  const { studyId: _studyId, articleId: _articleId } = useParams<{ studyId: string; articleId: string }>();
  const [studyId, articleId] = [Number(_studyId), Number(_articleId)];

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
          navigate(`../../${PATH.COMMUNITY}`, { replace: true });
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

const Loading = () => <div>Loading...</div>;

const Error = () => <div>Error...</div>;

type EditFormProps = {
  article: CommunityArticle;
  formMethods: UseFormReturn;
  onSubmit: HandleEditFormSubmit;
};
const EditForm: React.FC<EditFormProps> = ({ article, formMethods, onSubmit }) => (
  <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
    <ArticleTitleInput originalTitle={article.title} />
    <ArticleContentInput originalContent={article.content} />
    <Divider space="16px" />
    <ButtonGroup justifyContent="space-between">
      <GoBackLinkButton />
      <EditButton />
    </ButtonGroup>
  </Form>
);

const GoBackLinkButton: React.FC = () => (
  <Link to={`../../${PATH.COMMUNITY}`}>
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
