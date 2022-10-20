import { COMMUNITY_COMMENT_LENGTH } from '@constants';

import type { ArticleId, CommunityCommentId, DateYMD, Member, Noop, StudyId } from '@custom-types';

import { usePutCommunityComment } from '@api/community/comment';

import { type FieldElement, FormProvider, type UseFormSubmitResult, useForm } from '@hooks/useForm';

import ImportedCommentEditForm from '@study-room-page/components/comment-edit-form/CommentEditForm';

export type CommentEditFormProps = {
  studyId: StudyId;
  articleId: ArticleId;
  communityCommentId: CommunityCommentId;
  originalContent: string;
  date: DateYMD;
  author: Member;
  onEditSuccess: Noop;
  onEditError: (e: Error) => void;
  onCancelEditBtnClick: Noop;
};

const COMMENT_EDIT = 'comment-edit';

const CommentEditForm: React.FC<CommentEditFormProps> = ({
  studyId,
  articleId,
  communityCommentId,
  originalContent,
  date,
  author,
  onEditSuccess,
  onEditError,
  onCancelEditBtnClick: handleCancelEditButtonClick,
}) => {
  const formMethods = useForm();
  const {
    handleSubmit,
    formState: { errors },
  } = formMethods;
  const { mutateAsync } = usePutCommunityComment();

  const isValid = !errors[COMMENT_EDIT]?.hasError;

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    if (!submitResult.values) {
      return;
    }

    const content = submitResult.values[COMMENT_EDIT];

    return mutateAsync(
      { studyId, articleId, communityCommentId, content },
      {
        onSuccess: () => {
          onEditSuccess();
        },
        onError: error => {
          onEditError(error);
        },
      },
    );
  };

  return (
    <FormProvider {...formMethods}>
      <ImportedCommentEditForm
        author={author}
        date={date}
        maxLength={COMMUNITY_COMMENT_LENGTH.MAX.VALUE}
        renderField={setCount => {
          const handleChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);
          return (
            <ImportedCommentEditForm.CommentEditField
              name={COMMENT_EDIT}
              isValid={isValid}
              label="댓글 수정"
              placeholder="수정할 댓글을 입력해 주세요"
              defaultValue={originalContent}
              minLength={COMMUNITY_COMMENT_LENGTH.MIN.VALUE}
              minLengthErrorMessage={COMMUNITY_COMMENT_LENGTH.MIN.MESSAGE}
              maxLength={COMMUNITY_COMMENT_LENGTH.MAX.VALUE}
              maxLengthErrorMessage={COMMUNITY_COMMENT_LENGTH.MAX.MESSAGE}
              onChange={handleChange}
            />
          );
        }}
        onSubmit={handleSubmit(onSubmit)}
        onCancelEditButtonClick={handleCancelEditButtonClick}
      />
    </FormProvider>
  );
};

export default CommentEditForm;
