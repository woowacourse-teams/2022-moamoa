import { useEffect, useRef, useState } from 'react';
import { Link, Navigate, useNavigate, useParams } from 'react-router-dom';

import { CONTENT, DRAFT_SAVE_TIME, PATH, TITLE } from '@constants';

import type { DraftArtcle } from '@custom-types';

import {
  type ApiCommunityDraftArticleToArticle,
  useGetCommunityDraftArticle,
  usePostDraftArticleToArticle,
  usePutCommunityDraftArticle,
} from '@api/community/draft-article';

import { FormProvider, type UseFormReturn, type UseFormSubmitResult, useForm } from '@hooks/useForm';
import { useUserRole } from '@hooks/useUserRole';

import { BoxButton } from '@shared/button';
import ButtonGroup from '@shared/button-group/ButtonGroup';
import Divider from '@shared/divider/Divider';
import Form from '@shared/form/Form';
import PageTitle from '@shared/page-title/PageTitle';

import ArticleContentInput from '@components/article-content-input/ArticleContentInput';
import ArticleTitleInput from '@components/article-title-input/ArticleTitleInput';

import DraftSaveButton from '@community-tab/components/draft-save-button/DraftSaveButton';

type HandleDraftArticlePublishFormSubmit = (
  _: React.FormEvent<HTMLFormElement>,
  submitResult: UseFormSubmitResult,
) => Promise<ApiCommunityDraftArticleToArticle['post']['responseData'] | undefined>;

const DraftArticlePublish: React.FC = () => {
  const { studyId: _studyId, articleId: _articleId } = useParams<{ studyId: string; articleId: string }>();
  const [studyId, articleId] = [Number(_studyId), Number(_articleId)];

  const formMethods = useForm();
  const navigate = useNavigate();

  const { isFetching, isError, isSuccess, isOwnerOrMember } = useUserRole({ studyId });
  const draftArticleResponseData = useGetCommunityDraftArticle({ studyId, articleId });

  const draftTimeIntervalIdRef = useRef<NodeJS.Timeout | null>(null);

  const [isSaving, setIsSaving] = useState(false);

  const putDraftArticle = usePutCommunityDraftArticle();
  const { mutateAsync } = usePostDraftArticleToArticle();

  const changeSaveState = () => {
    setIsSaving(true);
    setTimeout(() => {
      setIsSaving(false);
    }, 1800);
  };

  const getElementValues = () => {
    const [titleElement, contentElement] = [formMethods.getField(TITLE), formMethods.getField(CONTENT)];
    if (!titleElement || !contentElement) return { title: '', content: '' };
    const [title, content] = [titleElement.fieldElement.value, contentElement.fieldElement.value];
    return { title, content };
  };

  useEffect(() => {
    draftTimeIntervalIdRef.current = setInterval(() => {
      const { title, content } = getElementValues();
      if (!title || !content) return;

      changeSaveState();

      putDraftArticle.mutate(
        { studyId, articleId, title, content },
        {
          onError: () => {
            console.error('오류');
          },
        },
      );
    }, DRAFT_SAVE_TIME.FIVE_MINUTES);

    return () => {
      draftTimeIntervalIdRef.current && clearInterval(draftTimeIntervalIdRef.current);
    };
  }, []);

  const handleDraftSaveButtonClick = () => {
    if (isSaving) return;

    const { title, content } = getElementValues();
    if (!title || !content) {
      alert('게시글 제목과 내용이 있어야 임시저장이 가능합니다.');
      return;
    }

    changeSaveState();

    putDraftArticle.mutate(
      { studyId, articleId, title, content },
      {
        onError: () => {
          // TODO: 메세지 알려주기
          console.error('오류');
        },
      },
    );
  };

  const handleSubmit: HandleDraftArticlePublishFormSubmit = async (_, { values }) => {
    if (!values) return;
    const { title, content } = values;
    if (!title || !content) return;

    return mutateAsync(
      {
        studyId,
        articleId,
        title,
        content,
      },
      {
        onError: () => {
          alert('글을 작성하지 못했습니다. 다시 시도해주세요. :(');
        },
        onSuccess: () => {
          draftTimeIntervalIdRef.current && clearInterval(draftTimeIntervalIdRef.current);
          alert('글을 작성했습니다. :D');
          navigate(`../../${PATH.COMMUNITY}`, { replace: true }); // TODO: 생성한 게시글 상세 페이지로 이동
        },
      },
    );
  };

  return (
    <FormProvider {...formMethods}>
      <PageTitle>게시글 작성</PageTitle>
      <DraftSaveButton isSaving={isSaving} onClick={handleDraftSaveButtonClick}>
        임시 저장
      </DraftSaveButton>
      <Divider space="16px" />
      {(() => {
        if (isFetching || draftArticleResponseData.isFetching) return <Loading />;
        if (isError || draftArticleResponseData.isError) return <Error />;
        if (isSuccess) {
          if (!isOwnerOrMember) {
            alert('접근할 수 없습니다!');
            return <Navigate to={`../../${PATH.COMMUNITY}`} replace />;
          }
          if (draftArticleResponseData.isSuccess) {
            const { title, content } = draftArticleResponseData.data;
            return <PublishForm title={title} content={content} formMethods={formMethods} onSubmit={handleSubmit} />;
          }
        }
      })()}
    </FormProvider>
  );
};

export default DraftArticlePublish;

const Loading = () => <div>유저 정보 가져오는 중...</div>;

const Error = () => <div>유저 정보를 가져오는 도중 에러가 발생했습니다.</div>;

type PublishFormProps = {
  formMethods: UseFormReturn;
  onSubmit: HandleDraftArticlePublishFormSubmit;
} & Pick<DraftArtcle, 'title' | 'content'>;

const PublishForm: React.FC<PublishFormProps> = ({ title, content, formMethods, onSubmit }) => (
  <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
    <ArticleTitleInput originalTitle={title} />
    <ArticleContentInput originalContent={content} />
    <Divider space="16px" />
    <ButtonGroup justifyContent="space-between">
      <ListPageLink />
      <PublishButton />
    </ButtonGroup>
  </Form>
);

const ListPageLink: React.FC = () => (
  <Link to={`../../${PATH.COMMUNITY}`}>
    <BoxButton type="button" variant="secondary" custom={{ padding: '4px 8px', fontSize: 'lg' }}>
      돌아가기
    </BoxButton>
  </Link>
);

const PublishButton: React.FC = () => (
  <BoxButton type="submit" fluid={false} custom={{ padding: '4px 8px', fontSize: 'lg' }}>
    등록하기
  </BoxButton>
);
