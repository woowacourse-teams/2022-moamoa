import { useNavigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { usePostCommunityArticle } from '@api/community';

import { FormProvider, UseFormSubmitResult, useForm } from '@hooks/useForm';

import { BoxButton } from '@design/components/button';
import ButtonGroup from '@design/components/button-group/ButtonGroup';
import Divider from '@design/components/divider/Divider';
import Form from '@design/components/form/Form';
import Title from '@design/components/title/Title';

import PublishContent from '@community-tab/components/publish-content/PublishContent';
import PublishTitle from '@community-tab/components/publish-title/PublishTitle';

const Publish = () => {
  const formMethods = useForm();
  const navigate = useNavigate();
  const { studyId } = useParams<{ studyId: string }>();
  const { mutateAsync } = usePostCommunityArticle();

  const handleGoToArticleListPageButtonClick = () => {
    navigate(`${PATH.COMMUNITY()}`);
  };

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    const { values } = submitResult;
    if (!values) return;

    const { title, content } = values;

    const numStudyId = Number(studyId);
    mutateAsync(
      {
        studyId: numStudyId,
        title,
        content,
      },
      {
        onSuccess: () => {
          alert('글 작성 완료!');
          navigate(PATH.COMMUNITY(numStudyId));
        },
        onError: () => {
          alert('글 작성 실패!');
        },
      },
    );
  };

  return (
    <FormProvider {...formMethods}>
      <Title.Page>게시글 작성</Title.Page>
      <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
        <PublishTitle />
        <PublishContent />
        <Divider space="16px" />
        <ButtonGroup justifyContent="space-between">
          <BoxButton
            type="button"
            variant="secondary"
            padding="4px 8px"
            fluid={false}
            onClick={handleGoToArticleListPageButtonClick}
          >
            돌아가기
          </BoxButton>
          <BoxButton type="submit" padding="4px 8px" fluid={false}>
            등록하기
          </BoxButton>
        </ButtonGroup>
      </Form>
    </FormProvider>
  );
};

export default Publish;
