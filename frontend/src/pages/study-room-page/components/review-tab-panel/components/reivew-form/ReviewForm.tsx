import type { AxiosError } from 'axios';
import { useMutation } from 'react-query';

import { REVIEW_LENGTH } from '@constants';

import type { Member, Noop, PostReviewRequestVariables, StudyId } from '@custom-types';

import { postReview } from '@api';

import { makeValidationResult, useForm } from '@hooks/useForm';
import type { FieldElement, UseFormSubmitResult } from '@hooks/useForm';

import Avatar from '@components/avatar/Avatar';
import { Button } from '@components/button/Button.style';
import LetterCounter from '@components/letter-counter/LetterCounter';
import useLetterCount from '@components/letter-counter/useLetterCount';

import * as S from '@study-room-page/components/review-tab-panel/components/reivew-form/ReviewForm.style';

export type ReviewFormProps = {
  studyId: StudyId;
  author: Member;
  onPostSuccess: Noop;
  onPostError: (e: Error) => void;
};

const ReviewForm: React.FC<ReviewFormProps> = ({ studyId, author, onPostSuccess, onPostError }) => {
  const { count, setCount, maxCount } = useLetterCount(REVIEW_LENGTH.MAX.VALUE);
  const { register, handleSubmit, reset } = useForm();
  const { mutateAsync } = useMutation<null, AxiosError, PostReviewRequestVariables>(postReview);

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    if (!submitResult.values) {
      return;
    }

    const content = submitResult.values['review'];

    return mutateAsync(
      { studyId, content },
      {
        onSuccess: () => {
          reset('review');
          onPostSuccess();
        },
        onError: error => {
          onPostError(error);
        },
      },
    );
  };

  const handleReviewChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);

  return (
    <S.ReviewForm onSubmit={handleSubmit(onSubmit)}>
      <S.ReviewFormHead>
        <S.UserInfo href={author.profileUrl}>
          <Avatar profileImg={author.imageUrl} profileAlt={`${author.username} 이미지`} size="xs" />
          <S.Username>{author.username}</S.Username>
        </S.UserInfo>
      </S.ReviewFormHead>
      <S.ReviewFormBody>
        <S.Textarea
          {...register('review', {
            validate: (val: string) => {
              if (val.length < REVIEW_LENGTH.MIN.VALUE) {
                return makeValidationResult(true, REVIEW_LENGTH.MIN.MESSAGE);
              }
              if (val.length > REVIEW_LENGTH.MAX.VALUE) return makeValidationResult(true, REVIEW_LENGTH.MAX.MESSAGE);
              return makeValidationResult(false);
            },
            validationMode: 'change',
            onChange: handleReviewChange,
            minLength: REVIEW_LENGTH.MIN.VALUE,
            maxLength: REVIEW_LENGTH.MAX.VALUE,
          })}
        ></S.Textarea>
      </S.ReviewFormBody>
      <S.ReviewFormFooter>
        <LetterCounter count={count} maxCount={maxCount} />
        <S.ButtonGroup>
          <Button className="register-btn">등록</Button>
        </S.ButtonGroup>
      </S.ReviewFormFooter>
    </S.ReviewForm>
  );
};

export default ReviewForm;
