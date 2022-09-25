import { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import type { ArticleId, StudyId } from '@custom-types';

import { useGetNoticeArticle, usePutNoticeArticle } from '@api/notice';

import { FormProvider, UseFormSubmitResult, useForm } from '@hooks/useForm';

import { BoxButton } from '@components/button';
import ButtonGroup from '@components/button-group/ButtonGroup';
import Divider from '@components/divider/Divider';
import Form from '@components/form/Form';
import PageTitle from '@components/page-title/PageTitle';

import EditContent from '@notice-tab/components/edit-content/EditContent';
import EditTitle from '@notice-tab/components/edit-title/EditTitle';
import usePermission from '@notice-tab/hooks/usePermission';

export type EditProps = {
  studyId: StudyId;
  articleId: ArticleId;
};

const Edit: React.FC<EditProps> = ({ studyId, articleId }) => {
  const formMethods = useForm();
  const navigate = useNavigate();

  const getNoticeArticleQueryResult = useGetNoticeArticle({ studyId, articleId });
  const { mutateAsync } = usePutNoticeArticle();

  const { isFetching, hasPermission } = usePermission(studyId, 'OWNER');

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
          alert('글을 수정했습니다 :D');
          navigate(`../${PATH.NOTICE}`);
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
        <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
          <EditTitle title={getNoticeArticleQueryResult.data.title} />
          <EditContent content={getNoticeArticleQueryResult.data.content} />
          <Divider space="16px" />
          <ButtonGroup justifyContent="space-between">
            <Link to={`../${PATH.NOTICE}`}>
              <BoxButton type="button" variant="secondary" padding="4px 8px" fluid={false} fontSize="lg">
                돌아가기
              </BoxButton>
            </Link>
            <BoxButton type="submit" padding="4px 8px" fluid={false} fontSize="lg">
              수정하기
            </BoxButton>
          </ButtonGroup>
        </Form>
      );
    }
  };

  return (
    <FormProvider {...formMethods}>
      <PageTitle>공지사항 수정</PageTitle>
      {render()}
    </FormProvider>
  );
};

export default Edit;
