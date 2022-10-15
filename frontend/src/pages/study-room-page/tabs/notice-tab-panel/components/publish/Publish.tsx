import { Link, useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import type { StudyId } from '@custom-types';

import { usePostNoticeArticle } from '@api/notice';

import { FormProvider, UseFormSubmitResult, useForm } from '@hooks/useForm';

import { BoxButton } from '@components/button';
import ButtonGroup from '@components/button-group/ButtonGroup';
import Divider from '@components/divider/Divider';
import Form from '@components/form/Form';
import PageTitle from '@components/page-title/PageTitle';

import PublishContent from '@notice-tab/components/publish-content/PublishContent';
import PublishTitle from '@notice-tab/components/publish-title/PublishTitle';

export type PublishProps = {
  studyId: StudyId;
};

const Publish: React.FC<PublishProps> = ({ studyId }) => {
  const formMethods = useForm();
  const navigate = useNavigate();
  const { mutateAsync } = usePostNoticeArticle();

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
      <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
        <PublishTitle />
        <PublishContent />
        <Divider space="16px" />
        <ButtonGroup justifyContent="space-between">
          <Link to={`../${PATH.NOTICE}`}>
            <BoxButton type="button" variant="secondary" padding="4px 8px" fluid={false} fontSize="lg">
              돌아가기
            </BoxButton>
          </Link>
          <BoxButton type="submit" padding="4px 8px" fluid={false} fontSize="lg">
            등록하기
          </BoxButton>
        </ButtonGroup>
      </Form>
    </FormProvider>
  );
};

export default Publish;
