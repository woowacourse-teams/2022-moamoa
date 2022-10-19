import { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import type { StudyId } from '@custom-types';

import { usePostCommunityArticle } from '@api/community';

import { FormProvider, type UseFormReturn, type UseFormSubmitResult, useForm } from '@hooks/useForm';
import { useUserRole } from '@hooks/useUserRole';

import { BoxButton } from '@shared/button';
import ButtonGroup from '@shared/button-group/ButtonGroup';
import Divider from '@shared/divider/Divider';
import Form from '@shared/form/Form';
import PageTitle from '@shared/page-title/PageTitle';

import ArticleContentInput from '@components/article-content-input/ArticleContentInput';
import ArticleTitleInput from '@components/article-title-input/ArticleTitleInput';

export type PublishProps = {
  studyId: StudyId;
};

type HandlePublishFormSubmit = (
  _: React.FormEvent<HTMLFormElement>,
  submitResult: UseFormSubmitResult,
) => Promise<null | undefined>;

const Publish: React.FC<PublishProps> = ({ studyId }) => {
  const formMethods = useForm();
  const navigate = useNavigate();
  const { mutateAsync } = usePostCommunityArticle();

  const { isFetching, isError, isOwnerOrMember } = useUserRole({ studyId });

  useEffect(() => {
    if (isFetching) return;
    if (isOwnerOrMember) return;

    alert('접근할 수 없습니다!');
    navigate(`../${PATH.COMMUNITY}`);
  }, [isFetching, isOwnerOrMember]);

  const handleSubmit: HandlePublishFormSubmit = async (_, submitResult) => {
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
          navigate(`../${PATH.COMMUNITY}`); // TODO: 생성한 게시글 상세 페이지로 이동
        },
        onError: () => {
          alert('글을 작성하지 못했습니다. 다시 시도해주세요. :(');
        },
      },
    );
  };

  return (
    <FormProvider {...formMethods}>
      <PageTitle>게시글 작성</PageTitle>
      {(() => {
        if (isFetching) return <Loading />;
        if (isError) return <Error />;
        if (isOwnerOrMember) return <PublishForm formMethods={formMethods} onSubmit={handleSubmit} />;
      })()}
    </FormProvider>
  );
};

export default Publish;

const Loading = () => <div>유저 정보 가져오는 중...</div>;

const Error = () => <div>유저 정보를 가져오는 도중 에러가 발생했습니다.</div>;

type PublishFormProps = {
  formMethods: UseFormReturn;
  onSubmit: HandlePublishFormSubmit;
};

const PublishForm: React.FC<PublishFormProps> = ({ formMethods, onSubmit }) => (
  <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
    <ArticleTitleInput />
    <ArticleContentInput />
    <Divider space="16px" />
    <ButtonGroup justifyContent="space-between">
      <GoBackLinkButton />
      <RegisterButton />
    </ButtonGroup>
  </Form>
);

const GoBackLinkButton: React.FC = () => (
  <Link to={`../${PATH.COMMUNITY}`}>
    <BoxButton type="button" variant="secondary" custom={{ padding: '4px 8px', fontSize: 'lg' }}>
      돌아가기
    </BoxButton>
  </Link>
);

const RegisterButton: React.FC = () => (
  <BoxButton type="submit" fluid={false} custom={{ padding: '4px 8px', fontSize: 'lg' }}>
    등록하기
  </BoxButton>
);
