import { REVIEW_LENGTH } from '@constants';

import type { Member, Noop, StudyId } from '@custom-types';

import { usePostReview } from '@api/review';

import { type FieldElement, FormProvider, type UseFormSubmitResult, useForm } from '@hooks/useForm';

import CommentForm from '@study-room-page/components/comment-form/CommentForm';

export type ReviewFormProps = {
  studyId: StudyId;
  author: Member;
  onPostSuccess: Noop;
  onPostError: (e: Error) => void;
};

const REVIEW = 'review';

const ReviewForm: React.FC<ReviewFormProps> = ({ studyId, author, onPostSuccess, onPostError }) => {
  const formMethods = useForm();
  const {
    handleSubmit,
    reset,
    formState: { errors },
  } = formMethods;
  const { mutateAsync } = usePostReview();

  const isValid = !errors[REVIEW]?.hasError;

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    if (!submitResult.values) {
      return;
    }

    const content = submitResult.values[REVIEW];

    return mutateAsync(
      { studyId, content },
      {
        onSuccess: () => {
          reset(REVIEW);
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
      <CommentForm
        author={author}
        maxLength={REVIEW_LENGTH.MAX.VALUE}
        renderField={setCount => {
          const handleChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);
          return (
            <CommentForm.CommentField
              name={REVIEW}
              isValid={isValid}
              label="리뷰 등록"
              placeholder="리뷰를 입력해 주세요"
              minLength={REVIEW_LENGTH.MIN.VALUE}
              minLengthErrorMessage={REVIEW_LENGTH.MIN.MESSAGE}
              maxLength={REVIEW_LENGTH.MAX.VALUE}
              maxLengthErrorMessage={REVIEW_LENGTH.MAX.MESSAGE}
              onChange={handleChange}
            />
          );
        }}
        onSubmit={handleSubmit(onSubmit)}
      />
    </FormProvider>
  );
};

export default ReviewForm;
