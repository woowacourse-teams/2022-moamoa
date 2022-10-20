import { NOTICE_COMMENT_LENGTH } from '@constants';

import type { ArticleId, DateYMD, Member, Noop, NoticeCommentId, StudyId } from '@custom-types';

import { usePutNoticeComment } from '@api/notice-comment';

import { type FieldElement, FormProvider, type UseFormSubmitResult, useForm } from '@hooks/useForm';

import ImportedCommentEditForm from '@study-room-page/components/comment-edit-form/CommentEditForm';

export type CommentEditFormProps = {
  studyId: StudyId;
  articleId: ArticleId;
  noticeCommentId: NoticeCommentId;
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
  noticeCommentId,
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
  const { mutateAsync } = usePutNoticeComment();

  const isValid = !errors[COMMENT_EDIT]?.hasError;

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    if (!submitResult.values) {
      return;
    }

    const content = submitResult.values[COMMENT_EDIT];

    return mutateAsync(
      { studyId, articleId, noticeCommentId, content },
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
        maxLength={NOTICE_COMMENT_LENGTH.MAX.VALUE}
        renderField={setCount => {
          const handleChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);
          return (
            <ImportedCommentEditForm.CommentEditField
              name={COMMENT_EDIT}
              isValid={isValid}
              label="댓글 수정"
              placeholder="수정할 댓글을 입력해 주세요"
              defaultValue={originalContent}
              minLength={NOTICE_COMMENT_LENGTH.MIN.VALUE}
              minLengthErrorMessage={NOTICE_COMMENT_LENGTH.MIN.MESSAGE}
              maxLength={NOTICE_COMMENT_LENGTH.MAX.VALUE}
              maxLengthErrorMessage={NOTICE_COMMENT_LENGTH.MAX.MESSAGE}
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
