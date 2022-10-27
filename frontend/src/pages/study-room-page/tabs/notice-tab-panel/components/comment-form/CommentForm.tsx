import { NOTICE_COMMENT_LENGTH } from '@constants';

import type { ArticleId, Member, Noop, StudyId } from '@custom-types';

import { usePostNoticeComment } from '@api/notice/comment';

import { type FieldElement, FormProvider, type UseFormSubmitResult, useForm } from '@hooks/useForm';

import ImportedCommentForm from '@study-room-page/components/comment-form/CommentForm';

const NOTICE_COMMENT = 'notice-comment';

type CommentFormProps = {
  studyId: StudyId;
  articleId: ArticleId;
  author: Member;
  onPostSuccess: Noop;
  onPostError: (e: Error) => void;
};
const CommentForm: React.FC<CommentFormProps> = ({ studyId, articleId, author, onPostSuccess, onPostError }) => {
  const formMethods = useForm();
  const {
    handleSubmit,
    reset,
    formState: { errors },
  } = formMethods;
  const { mutateAsync } = usePostNoticeComment();

  const isValid = !errors[NOTICE_COMMENT]?.hasError;

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    if (!submitResult.values) {
      return;
    }

    const content = submitResult.values[NOTICE_COMMENT];

    return mutateAsync(
      { studyId, articleId, content },
      {
        onSuccess: () => {
          reset(NOTICE_COMMENT);
          onPostSuccess();
        },
        onError: error => {
          onPostError(error);
        },
      },
    );
  };
  return (
    <FormProvider {...formMethods}>
      <ImportedCommentForm
        author={author}
        maxLength={NOTICE_COMMENT_LENGTH.MAX.VALUE}
        onSubmit={handleSubmit(onSubmit)}
        renderField={setCount => {
          const handleChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);
          return (
            <ImportedCommentForm.CommentField
              name={NOTICE_COMMENT}
              isValid={isValid}
              label="댓글 작성"
              placeholder="댓글을 입력해 주세요"
              minLength={NOTICE_COMMENT_LENGTH.MIN.VALUE}
              minLengthErrorMessage={NOTICE_COMMENT_LENGTH.MIN.MESSAGE}
              maxLength={NOTICE_COMMENT_LENGTH.MAX.VALUE}
              maxLengthErrorMessage={NOTICE_COMMENT_LENGTH.MAX.MESSAGE}
              onChange={handleChange}
            />
          );
        }}
      />
    </FormProvider>
  );
};

export default CommentForm;
