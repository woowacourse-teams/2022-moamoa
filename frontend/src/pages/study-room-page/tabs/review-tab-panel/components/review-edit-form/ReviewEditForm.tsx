import { REVIEW_LENGTH } from '@constants';

import type { DateYMD, Member, Noop, ReviewId, StudyId } from '@custom-types';

import { usePutReview } from '@api/review';

import { type FieldElement, FormProvider, type UseFormSubmitResult, useForm } from '@hooks/useForm';

import CommentEditForm from '@study-room-page/components/comment-edit-form/CommentEditForm';

export type ReviewEditFormProps = {
  studyId: StudyId;
  reviewId: ReviewId;
  originalContent: string;
  date: DateYMD;
  author: Member;
  onEditSuccess: Noop;
  onEditError: (e: Error) => void;
  onCancelEditBtnClick: Noop;
};

const REVIEW_EDIT = 'review-edit';

const ReviewEditForm: React.FC<ReviewEditFormProps> = ({
  studyId,
  reviewId,
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
  const { mutateAsync } = usePutReview();

  const isValid = !errors[REVIEW_EDIT]?.hasError;

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    if (!submitResult.values) {
      return;
    }

    const content = submitResult.values[REVIEW_EDIT];

    return mutateAsync(
      { studyId, reviewId, content },
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
      <CommentEditForm
        author={author}
        date={date}
        maxLength={REVIEW_LENGTH.MAX.VALUE}
        renderField={setCount => {
          const handleChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);
          return (
            <CommentEditForm.CommentEditField
              name={REVIEW_EDIT}
              isValid={isValid}
              label="리뷰 등록"
              placeholder="리뷰를 입력해 주세요"
              defaultValue={originalContent}
              minLength={REVIEW_LENGTH.MIN.VALUE}
              minLengthErrorMessage={REVIEW_LENGTH.MIN.MESSAGE}
              maxLength={REVIEW_LENGTH.MAX.VALUE}
              maxLengthErrorMessage={REVIEW_LENGTH.MAX.MESSAGE}
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

export default ReviewEditForm;
