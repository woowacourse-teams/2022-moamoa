import PublishContent from '@notice-tab/components/publish-content/PublishContent';
import PublishTitle from '@notice-tab/components/publish-title/PublishTitle';
import usePermission from '@notice-tab/hooks/usePermission';
import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { usePostNoticeArticle } from '@api/notice';

import { FormProvider, UseFormSubmitResult, useForm } from '@hooks/useForm';

import { BoxButton } from '@components/button';
import ButtonGroup from '@components/button-group/ButtonGroup';
import Divider from '@components/divider/Divider';
import Form from '@components/form/Form';
import PageTitle from '@components/page-title/PageTitle';

const Publish = () => {
  const formMethods = useForm();
  const navigate = useNavigate();
  const { studyId } = useParams() as { studyId: string };
  const { mutateAsync } = usePostNoticeArticle();
  const { isFetching, isError, hasPermission } = usePermission(studyId, 'OWNER');

  useEffect(() => {
    if (isFetching) return;
    if (hasPermission) return;

    alert('접근할 수 없습니다!');
    navigate(PATH.NOTICE(studyId));
  }, [studyId, navigate, isFetching, hasPermission]);

  const handleGoToArticleListPageButtonClick = () => {
    navigate(`${PATH.NOTICE()}`);
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
          navigate(PATH.NOTICE(numStudyId));
        },
        onError: () => {
          alert('글 작성 실패!');
        },
      },
    );
  };

  if (isFetching) {
    return <div>유저 정보 가져오는중...</div>;
  }

  if (isError) {
    return <div>유저 정보를 가져오는도중 에러를 만났습니다</div>;
  }

  return (
    <FormProvider {...formMethods}>
      <PageTitle>공지사항 작성</PageTitle>
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
            fontSize="lg"
            onClick={handleGoToArticleListPageButtonClick}
          >
            돌아가기
          </BoxButton>
          <BoxButton type="submit" padding="4px 8px" fluid={false} fontSize="lg">
            등록하기
          </BoxButton>
        </ButtonGroup>
      </Form>
    </FormProvider>
  );
};

export default Publish;
