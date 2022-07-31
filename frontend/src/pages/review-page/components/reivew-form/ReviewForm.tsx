import * as S from '@review-page/components/reivew-form/ReviewForm.style';
import { useState } from 'react';
import { useMutation } from 'react-query';

import { REVIEW_LENGTH } from '@constants';

import noop from '@utils/noop';

import { EmptyObject, ReviewQueryData, StudyId } from '@custom-types';

import { postReview } from '@api/postReview';

import { makeValidationResult, useForm } from '@hooks/useForm';
import type { UseFormSubmitResult } from '@hooks/useForm';

import LetterCounter from '@components/letter-counter/LetterCounter';

export type ReviewFormProps = {
  studyId: StudyId;
  onPostSuccess: () => void;
  onPostError: (e: Error) => void;
};

const ReviewForm: React.FC<ReviewFormProps> = ({ studyId, onPostSuccess, onPostError }) => {
  const [count, setCount] = useState<number>(0);
  const maxLength = 200;
  const { register, handleSubmit } = useForm();
  const { mutateAsync } = useMutation<EmptyObject, Error, ReviewQueryData>(postReview);

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    if (!submitResult.values) {
      return;
    }

    const content = submitResult.values['review'];

    return mutateAsync(
      { studyId, content },
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
    <S.ReviewForm onSubmit={handleSubmit(onSubmit)}>
      <div className="left">
        <div className="top">
          <span>후기를 작성해주세요.</span>
          <LetterCounter count={count} maxCount={maxLength} />
        </div>
        <div className="bottom">
          <textarea
            {...register('review', {
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
        </div>
      </div>
      <div className="right">
        <button>제출</button>
      </div>
    </S.ReviewForm>
  );
};

export default ReviewForm;
