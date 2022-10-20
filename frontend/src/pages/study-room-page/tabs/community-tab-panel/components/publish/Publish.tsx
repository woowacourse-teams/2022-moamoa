import { useEffect, useRef, useState } from 'react';
import { Link, Navigate, useNavigate, useParams } from 'react-router-dom';

import { CONTENT, DRAFT_SAVE_TIME, PATH, TITLE } from '@constants';

import { usePostCommunityArticle } from '@api/community/article';
import {
  ApiCommunityDraftArticleToArticle,
  usePostCommunityDraftArticle,
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

type HandlePublishFormSubmit = (
  _: React.FormEvent<HTMLFormElement>,
  submitResult: UseFormSubmitResult,
) => Promise<ApiCommunityDraftArticleToArticle['post']['responseData'] | null | undefined>;

const Publish: React.FC = () => {
  const { studyId: _studyId } = useParams<{ studyId: string }>();
  const studyId = Number(_studyId);

  const formMethods = useForm();
  const navigate = useNavigate();

  const { isFetching, isError, isOwnerOrMember } = useUserRole({ studyId });

  const draftTimeIntervalIdRef = useRef<NodeJS.Timeout | null>(null);
  const initDraftTimeIntervalIdRef = useRef<NodeJS.Timeout | null>(null);

  const [isSaving, setIsSaving] = useState(false);

  const { mutateAsync } = usePostCommunityArticle();
  const postDraftArticle = usePostCommunityDraftArticle();
  const putDraftArticle = usePutCommunityDraftArticle();
  const postDraftArticleToArticle = usePostDraftArticleToArticle();

  const draftArticleId = postDraftArticle.data?.draftArticleId;

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
    if (isSaving) return;

    initDraftTimeIntervalIdRef.current = setInterval(() => {
      const { title, content } = getElementValues();
      if (!title || !content) return;

      changeSaveState();

      postDraftArticle.mutate(
        { studyId, title, content },
        {
          onError: () => {
            // TODO: 메세지 알려주기
            console.error('임시저장 오류');
            initDraftTimeIntervalIdRef.current && clearInterval(initDraftTimeIntervalIdRef.current);
          },
          onSuccess: () => {
            initDraftTimeIntervalIdRef.current && clearInterval(initDraftTimeIntervalIdRef.current);
          },
        },
      );
    }, DRAFT_SAVE_TIME.THIRTY_SECONDS);
  }, []);

  useEffect(() => {
    if (!draftArticleId) return;

    draftTimeIntervalIdRef.current = setInterval(() => {
      const { title, content } = getElementValues();
      if (!title || !content) return;

      changeSaveState();

      putDraftArticle.mutate(
        { studyId, articleId: draftArticleId, title, content },
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
  }, [postDraftArticle.data]);

  const handleDraftSaveButtonClick = () => {
    if (isSaving) return;

    const { title, content } = getElementValues();
    if (!title || !content) {
      alert('게시글 제목과 내용이 있어야 임시저장이 가능합니다.');
      return;
    }

    if (!draftArticleId) return;
    changeSaveState();

    putDraftArticle.mutate(
      { studyId, articleId: draftArticleId, title, content },
      {
        onError: () => {
          // TODO: 메세지 알려주기
          console.error('오류');
        },
      },
    );
  };

  const handleSubmit: HandlePublishFormSubmit = async (_, { values }) => {
    if (!values) return;
    const { title, content } = values;
    if (!title || !content) return;

    if (draftArticleId) {
      return postDraftArticleToArticle.mutateAsync(
        { studyId, articleId: draftArticleId, title, content },
        {
          onSuccess: () => {
            draftTimeIntervalIdRef.current && clearInterval(draftTimeIntervalIdRef.current);
            alert('글을 작성했습니다. :D');
            navigate(`../../${PATH.COMMUNITY}`, { replace: true }); // TODO: 생성한 게시글 상세 페이지로 이동
          },
          onError: () => {
            alert('글을 작성하지 못했습니다. 다시 시도해주세요. :(');
          },
        },
      );
    }

    return mutateAsync(
      {
        studyId,
        title,
        content,
      },
      {
        onSuccess: () => {
          draftTimeIntervalIdRef.current && clearInterval(draftTimeIntervalIdRef.current);
          alert('글을 작성했습니다. :D');
          navigate(`../../${PATH.COMMUNITY}`, { replace: true }); // TODO: 생성한 게시글 상세 페이지로 이동
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
      <DraftSaveButton isSaving={isSaving} onClick={handleDraftSaveButtonClick}>
        임시 저장
      </DraftSaveButton>
      <Divider space="16px" />
      {(() => {
        if (isFetching) return <Loading />;
        if (isError) return <Error />;
        if (!isOwnerOrMember) {
          alert('접근할 수 없습니다!');
          return <Navigate to={`../../${PATH.COMMUNITY}`} replace />;
        }
        return <PublishForm formMethods={formMethods} onSubmit={handleSubmit} />;
      })()}
    </FormProvider>
  );
};

export default Publish;

const Loading = () => <div>유저 정보 가져오는 중...</div>;

const Error = () => <div>유저 정보를 가져오는 도중 에러가 발생했습니다.</div>;

type PublishFormProps = {
  formMethods: UseFormReturn;
  onSubmit: HandlePublishFormSubmit;
};

const PublishForm: React.FC<PublishFormProps> = ({ formMethods, onSubmit }) => (
  <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
    <ArticleTitleInput />
    <ArticleContentInput />
    <Divider space="16px" />
    <ButtonGroup justifyContent="space-between">
      <GoBackLinkButton />
      <RegisterButton />
    </ButtonGroup>
  </Form>
);

const GoBackLinkButton: React.FC = () => (
  <Link to={`../../${PATH.COMMUNITY}`}>
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
