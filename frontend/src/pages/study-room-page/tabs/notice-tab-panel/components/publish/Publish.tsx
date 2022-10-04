import { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import { Theme, useTheme } from '@emotion/react';

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
import usePermission from '@notice-tab/hooks/usePermission';

export type PublishProps = {
  studyId: StudyId;
};

const Publish: React.FC<PublishProps> = ({ studyId }) => {
  const theme = useTheme();
  const formMethods = useForm();
  const navigate = useNavigate();
  const { mutateAsync } = usePostNoticeArticle();

  const { isFetching, isError, hasPermission } = usePermission(studyId, 'OWNER');

  useEffect(() => {
    if (isFetching) return;
    if (hasPermission) return;

    alert('접근할 수 없습니다!');
    navigate(`../${PATH.NOTICE}`);
  }, [studyId, navigate, isFetching, hasPermission]);

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
      {isFetching && <Loading />}
      {isError && <Error />}
      {hasPermission && (
        <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
          <PublishTitle />
          <PublishContent />
          <Divider space="16px" />
          <ButtonGroup justifyContent="space-between">
            <GoToListPageButton theme={theme} />
            <PublishButton theme={theme} />
          </ButtonGroup>
        </Form>
      )}
    </FormProvider>
  );
};

const Loading = () => <div>유저 정보 가져오는 중...</div>;

const Error = () => <div>유저 정보를 가져오는 도중 에러가 발생했습니다.</div>;

type GoToListPageButtonProps = {
  theme: Theme;
};
const GoToListPageButton: React.FC<GoToListPageButtonProps> = ({ theme }) => (
  <Link to={`../${PATH.COMMUNITY}`}>
    <BoxButton type="button" variant="secondary" custom={{ padding: '4px 8px', fontSize: theme.fontSize.lg }}>
      돌아가기
    </BoxButton>
  </Link>
);

type PublishButtonProps = {
  theme: Theme;
};
const PublishButton: React.FC<PublishButtonProps> = ({ theme }) => (
  <BoxButton type="submit" fluid={false} custom={{ padding: '4px 8px', fontSize: theme.fontSize.lg }}>
    등록하기
  </BoxButton>
);

export default Publish;
