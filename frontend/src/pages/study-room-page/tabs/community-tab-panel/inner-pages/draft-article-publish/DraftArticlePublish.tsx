import { Link, Navigate, useNavigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { DraftArtcle } from '@custom-types';

import {
  ApiCommunityDraftArticle,
  useGetCommunityDraftArticle,
  usePostDraftArticle,
} from '@api/community/draft-article';

import { FormProvider, type UseFormReturn, type UseFormSubmitResult, useForm } from '@hooks/useForm';
import { useUserRole } from '@hooks/useUserRole';

import { BoxButton } from '@shared/button';
import ButtonGroup from '@shared/button-group/ButtonGroup';
import Divider from '@shared/divider/Divider';
import Form from '@shared/form/Form';
import PageTitle from '@shared/page-title/PageTitle';

import ArticleContentInput from '@components/article-content-input/ArticleContentInput';
import ArticleTitleInput from '@components/article-title-input/ArticleTitleInput';

type HandleDraftArticlePublishFormSubmit = (
  _: React.FormEvent<HTMLFormElement>,
  submitResult: UseFormSubmitResult,
) => Promise<ApiCommunityDraftArticle['post']['responseData'] | undefined>;

const DraftArticlePublish: React.FC = () => {
  const { studyId: _studyId, articleId: _articleId } = useParams<{ studyId: string; articleId: string }>();
  const [studyId, articleId] = [Number(_studyId), Number(_articleId)];

  const formMethods = useForm();
  const navigate = useNavigate();

  const { isFetching, isError, isSuccess, isOwnerOrMember } = useUserRole({ studyId });
  const draftArticleResponseData = useGetCommunityDraftArticle({ studyId, articleId });
  const { mutateAsync } = usePostDraftArticle();

  const handleSubmit: HandleDraftArticlePublishFormSubmit = async (_, submitResult) => {
    const { values } = submitResult;
    if (!values) return;

    const { title, content } = values;

    return mutateAsync(
      {
        studyId,
        title,
        content,
      },
      {
        onSuccess: () => {
          alert('글을 작성했습니다. :D');
          navigate(`../${PATH.NOTICE}`); // TODO: 생성한 게시글 상세 페이지로 이동
        },
        onError: () => {
          alert('글을 작성하지 못했습니다. 다시 시도해주세요. :(');
        },
      },
    );
  };

  return (
    <FormProvider {...formMethods}>
      <PageTitle>공지사항 작성</PageTitle>
      {(() => {
        if (isFetching || draftArticleResponseData.isFetching) return <Loading />;
        if (isError || draftArticleResponseData.isError) return <Error />;
        if (isSuccess) {
          if (!isOwnerOrMember) {
            alert('접근할 수 없습니다!');
            return <Navigate to={`../../${PATH.COMMUNITY}`} replace />;
          }
          if (draftArticleResponseData.isSuccess) {
            const { title, content } = draftArticleResponseData.data;
            return <PublishForm title={title} content={content} formMethods={formMethods} onSubmit={handleSubmit} />;
          }
        }
      })()}
    </FormProvider>
  );
};

export default DraftArticlePublish;

const Loading = () => <div>유저 정보 가져오는 중...</div>;

const Error = () => <div>유저 정보를 가져오는 도중 에러가 발생했습니다.</div>;

type PublishFormProps = {
  formMethods: UseFormReturn;
  onSubmit: HandleDraftArticlePublishFormSubmit;
} & Pick<DraftArtcle, 'title' | 'content'>;

const PublishForm: React.FC<PublishFormProps> = ({ title, content, formMethods, onSubmit }) => (
  <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
    <ArticleTitleInput originalTitle={title} />
    <ArticleContentInput originalContent={content} />
    <Divider space="16px" />
    <ButtonGroup justifyContent="space-between">
      <ListPageLink />
      <PublishButton />
    </ButtonGroup>
  </Form>
);

const ListPageLink: React.FC = () => (
  <Link to={`../${PATH.COMMUNITY}`}>
    <BoxButton type="button" variant="secondary" custom={{ padding: '4px 8px', fontSize: 'lg' }}>
      돌아가기
    </BoxButton>
  </Link>
);

const PublishButton: React.FC = () => (
  <BoxButton type="submit" fluid={false} custom={{ padding: '4px 8px', fontSize: 'lg' }}>
    등록하기
  </BoxButton>
);
