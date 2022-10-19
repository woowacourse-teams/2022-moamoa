import { Link, useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import type { StudyId } from '@custom-types';

import { usePostCommunityArticle } from '@api/community';

import { FormProvider, UseFormSubmitResult, useForm } from '@hooks/useForm';

import { BoxButton } from '@shared/button';
import ButtonGroup from '@shared/button-group/ButtonGroup';
import Divider from '@shared/divider/Divider';
import Form from '@shared/form/Form';
import PageTitle from '@shared/page-title/PageTitle';

import PublishContent from '@community-tab/components/publish-content/PublishContent';
import PublishTitle from '@community-tab/components/publish-title/PublishTitle';

export type DraftArticlePublishProps = {
  studyId: StudyId;
};

const DraftArticlePublish: React.FC<DraftArticlePublishProps> = ({ studyId }) => {
  const formMethods = useForm();
  const navigate = useNavigate();
  const { mutateAsync } = usePostCommunityArticle();

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    const { values } = submitResult;
    if (!values) return;

    const { title, content } = values;

    mutateAsync(
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
      <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
        <PublishTitle />
        <PublishContent />
        <Divider space="16px" />
        <ButtonGroup justifyContent="space-between">
          <GoBackLinkButton />
          <RegisterButton />
        </ButtonGroup>
      </Form>
    </FormProvider>
  );
};

export default DraftArticlePublish;

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
