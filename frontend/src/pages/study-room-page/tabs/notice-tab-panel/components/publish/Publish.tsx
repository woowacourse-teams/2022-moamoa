import PublishContent from '@notice-tab/components/publish-content/PublishContent';
import PublishTitle from '@notice-tab/components/publish-title/PublishTitle';
import * as S from '@notice-tab/components/publish/Publish.style';
import { useNavigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { usePostNoticeArticle } from '@api/notice';

import { FormProvider, UseFormSubmitResult, useForm } from '@hooks/useForm';

const Publish = () => {
  const formMethods = useForm();
  const navigate = useNavigate();
  const { studyId } = useParams<{ studyId: string }>();
  const { mutateAsync } = usePostNoticeArticle();

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

  return (
    <FormProvider {...formMethods}>
      <form onSubmit={formMethods.handleSubmit(onSubmit)}>
        <PublishTitle />
        <PublishContent />
        <S.Footer>
          <S.Button type="button" onClick={handleGoToArticleListPageButtonClick}>
            돌아가기
          </S.Button>
          <S.Button type="submit">등록하기</S.Button>
        </S.Footer>
      </form>
    </FormProvider>
  );
};

export default Publish;
