import * as S from '@review-page/components/review-edit-form/ReviewEditForm.style';
import { useState } from 'react';
import { useMutation } from 'react-query';

import { REVIEW_LENGTH } from '@constants';

import { EditReviewQueryData, EmptyObject, ReviewId, StudyId } from '@custom-types';

import { editReview } from '@api/editReview';

import { makeValidationResult, useForm } from '@hooks/useForm';
import type { UseFormSubmitResult } from '@hooks/useForm';

import LetterCounter from '@components/letter-counter/LetterCounter';

export type ReviewEditFormProps = {
  studyId: StudyId;
  reviewId: ReviewId;
  originalContent: string;
  onPostSuccess: () => void;
  onPostError: (e: Error) => void;
  onCancelEditBtnClick: () => void;
};

const ReviewEditForm: React.FC<ReviewEditFormProps> = ({
  studyId,
  reviewId,
  originalContent,
  onPostSuccess,
  onPostError,
  onCancelEditBtnClick,
}) => {
  const [count, setCount] = useState<number>(originalContent.length);
  const maxLength = 200;
  const { register, handleSubmit } = useForm();
  const { mutateAsync } = useMutation<EmptyObject, Error, EditReviewQueryData>(editReview);

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    if (!submitResult.values) {
      return;
    }

    const content = submitResult.values['review-edit'];

    return mutateAsync(
      { studyId, reviewId, content },
      {
        onSuccess: () => {
          onPostSuccess();
        },
        onError: error => {
          onPostError(error);
        },
      },
    );
  };

  return (
    <S.ReviewEditForm onSubmit={handleSubmit(onSubmit)}>
      <div className="left">
        <div className="top">
          <textarea
            defaultValue={originalContent}
            {...register('review-edit', {
              validate: (val: string) => {
                if (val.length < REVIEW_LENGTH.MIN.VALUE) {
                  return makeValidationResult(true, REVIEW_LENGTH.MIN.MESSAGE);
                }
                if (val.length > REVIEW_LENGTH.MAX.VALUE) return makeValidationResult(true, REVIEW_LENGTH.MAX.MESSAGE);
                return makeValidationResult(false);
              },
              validationMode: 'change',
              onChange: e => setCount(e.target.value.length),
              minLength: REVIEW_LENGTH.MIN.VALUE,
              maxLength: REVIEW_LENGTH.MAX.VALUE,
            })}
          />
          <LetterCounter count={count} maxCount={maxLength} />
        </div>
        <div className="bottom">
          <button onClick={onCancelEditBtnClick}>취소</button>
        </div>
      </div>
      <div className="right">
        <button>수정</button>
      </div>
    </S.ReviewEditForm>
  );
};

export default ReviewEditForm;
