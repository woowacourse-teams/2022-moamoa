import EditContent from '@notice-tab/components/edit-content/EditContent';
import EditTitle from '@notice-tab/components/edit-title/EditTitle';
import * as S from '@notice-tab/components/edit/Edit.style';
import usePermission from '@notice-tab/hooks/usePermission';
import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { useGetNoticeArticle, usePutNoticeArticle } from '@api/notice';

import { FormProvider, UseFormSubmitResult, useForm } from '@hooks/useForm';

const Edit = () => {
  const formMethods = useForm();
  const navigate = useNavigate();
  const { studyId, articleId } = useParams() as { studyId: string; articleId: string };
  const numStudyId = Number(studyId);
  const numArticleId = Number(articleId);

  const getNoticeArticleQueryResult = useGetNoticeArticle(numStudyId, numArticleId);
  const { mutateAsync } = usePutNoticeArticle();

  const { isFetching, hasPermission } = usePermission(studyId, 'OWNER');

  useEffect(() => {
    if (isFetching) return;
    if (hasPermission) return;

    alert('접근할 수 없습니다!');
    navigate(PATH.NOTICE(studyId));
  }, [studyId, navigate, isFetching, hasPermission]);

  const handleGoToArticlePageButtonClick = () => {
    navigate(`${PATH.NOTICE_ARTICLE(studyId, articleId)}`);
  };

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    const { values } = submitResult;
    if (!values) return;

    const { title, content } = values;

    const numStudyId = Number(studyId);
    const numArticleId = Number(articleId);
    mutateAsync(
      {
        studyId: numStudyId,
        articleId: numArticleId,
        title,
        content,
      },
      {
        onSuccess: () => {
          alert('글 수정 완료!');
          navigate(PATH.NOTICE(numStudyId));
        },
        onError: () => {
          alert('글 수정 실패!');
        },
      },
    );
  };

  const render = () => {
    if (getNoticeArticleQueryResult.isFetching) {
      return <div>Loading...</div>;
    }

    if (getNoticeArticleQueryResult.isError) {
      return <div>Error...</div>;
    }

    if (getNoticeArticleQueryResult.isSuccess) {
      return (
        <form onSubmit={formMethods.handleSubmit(onSubmit)}>
          <EditTitle title={getNoticeArticleQueryResult.data.title} />
          <EditContent content={getNoticeArticleQueryResult.data.content} />
          <S.Footer>
            <S.Button type="button" onClick={handleGoToArticlePageButtonClick}>
              돌아가기
            </S.Button>
            <S.Button type="submit">수정하기</S.Button>
          </S.Footer>
        </form>
      );
    }
  };

  return <FormProvider {...formMethods}>{render()}</FormProvider>;
};

export default Edit;
