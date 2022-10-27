import { COMMUNITY_COMMENT_LENGTH } from '@constants';

import { ArticleId, Member, Noop, StudyId } from '@custom-types';

import { usePostCommunityComment } from '@api/community/comment';

import { FieldElement, FormProvider, UseFormSubmitResult, useForm } from '@hooks/useForm';

import ImportedCommentForm from '@study-room-page/components/comment-form/CommentForm';

const COMMUNITY_COMMENT = 'community-comment';

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
  const { mutateAsync } = usePostCommunityComment();

  const isValid = !errors[COMMUNITY_COMMENT]?.hasError;

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    if (!submitResult.values) {
      return;
    }

    const content = submitResult.values[COMMUNITY_COMMENT];

    return mutateAsync(
      { studyId, articleId, content },
      {
        onSuccess: () => {
          reset(COMMUNITY_COMMENT);
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
        maxLength={200}
        onSubmit={handleSubmit(onSubmit)}
        renderField={setCount => {
          const handleChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);
          return (
            <ImportedCommentForm.CommentField
              name={COMMUNITY_COMMENT}
              isValid={isValid}
              label="댓글 작성"
              placeholder="댓글을 입력해 주세요"
              minLength={COMMUNITY_COMMENT_LENGTH.MIN.VALUE}
              minLengthErrorMessage={COMMUNITY_COMMENT_LENGTH.MIN.MESSAGE}
              maxLength={COMMUNITY_COMMENT_LENGTH.MAX.VALUE}
              maxLengthErrorMessage={COMMUNITY_COMMENT_LENGTH.MAX.MESSAGE}
              onChange={handleChange}
            />
          );
        }}
      />
    </FormProvider>
  );
};

export default CommentForm;
