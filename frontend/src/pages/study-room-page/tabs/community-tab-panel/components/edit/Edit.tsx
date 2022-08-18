import { useNavigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { useGetCommunityArticle, usePostCommunityArticle } from '@api/community';

import { FormProvider, UseFormSubmitResult, useForm } from '@hooks/useForm';

import EditContent from '@community-tab/components/edit-content/EditContent';
import EditTitle from '@community-tab/components/edit-title/EditTitle';
import * as S from '@community-tab/components/edit/Edit.style';

const Edit = () => {
  const formMethods = useForm();
  const navigate = useNavigate();
  const { studyId, articleId } = useParams<{ studyId: string; articleId: string }>();
  const numStudyId = Number(studyId);
  const numArticleId = Number(articleId);

  const getCommunityArticleQueryResult = useGetCommunityArticle(numStudyId, numArticleId);
  const { mutateAsync } = usePostCommunityArticle();

  const handleGoToArticlePageButtonClick = () => {
    navigate(`${PATH.COMMUNITY_ARTICLE(studyId, articleId)}`);
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
          alert('글 수정 완료!');
          navigate(PATH.COMMUNITY(numStudyId));
        },
        onError: () => {
          alert('글 작성 실패!');
        },
      },
    );
  };

  const render = () => {
    if (getCommunityArticleQueryResult.isFetching) {
      return <div>Loading...</div>;
    }

    if (getCommunityArticleQueryResult.isError) {
      return <div>Error...</div>;
    }

    if (getCommunityArticleQueryResult.isSuccess) {
      return (
        <form onSubmit={formMethods.handleSubmit(onSubmit)}>
          <EditTitle title={getCommunityArticleQueryResult.data.title} />
          <EditContent content={getCommunityArticleQueryResult.data.content} />
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
