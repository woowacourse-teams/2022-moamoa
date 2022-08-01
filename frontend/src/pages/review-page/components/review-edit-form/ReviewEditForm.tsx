import { useMutation } from 'react-query';

import { REVIEW_LENGTH } from '@constants';

import { changeDateSeperator } from '@utils/dates';

import { DateYMD, EditReviewQueryData, EmptyObject, Member, ReviewId, StudyId } from '@custom-types';

import { patchReview } from '@api/patchReview';

import { makeValidationResult, useForm } from '@hooks/useForm';
import type { UseFormSubmitResult } from '@hooks/useForm';

import Avatar from '@components/avatar/Avatar';
import Button from '@components/button/Button';
import LetterCounter from '@components/letter-counter/LetterCounter';
import useLetterCount from '@components/letter-counter/useLetterCount';

import * as S from '@review-page/components/review-edit-form/ReviewEditForm.style';

export type ReviewEditFormProps = {
  studyId: StudyId;
  reviewId: ReviewId;
  originalContent: string;
  date: DateYMD;
  author: Member;
  onEditSuccess: () => void;
  onEditError: (e: Error) => void;
  onCancelEditBtnClick: () => void;
};

const ReviewEditForm: React.FC<ReviewEditFormProps> = ({
  studyId,
  reviewId,
  originalContent,
  date,
  author,
  onEditSuccess,
  onEditError,
  onCancelEditBtnClick,
}) => {
  const { count, setCount, maxCount } = useLetterCount(REVIEW_LENGTH.MAX.VALUE, originalContent.length);
  const { register, handleSubmit } = useForm();
  const { mutateAsync } = useMutation<EmptyObject, Error, EditReviewQueryData>(patchReview);

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    if (!submitResult.values) {
      return;
    }

    const content = submitResult.values['review-edit'];

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
    <S.ReviewEditForm onSubmit={handleSubmit(onSubmit)}>
      <div className="textarea-container">
        <div className="top">
          <div className="user-info">
            <div className="left">
              <a href={author.profileUrl}>
                <Avatar profileImg={author.imageUrl} profileAlt="EMPTY" size="sm" />
              </a>
            </div>
            <div className="right">
              <a href={author.profileUrl}>
                <span className="username">{author.username}</span>
              </a>
              <span className="date">{changeDateSeperator(date)}</span>
            </div>
          </div>
        </div>
        <div className="middle">
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
        </div>
        <div className="bottom">
          <LetterCounter count={count} maxCount={maxCount} />
          <div className="btn-group">
            <Button className="cancel-btn" type="button" onClick={onCancelEditBtnClick}>
              취소
            </Button>
            <Button className="register-btn">수정</Button>
          </div>
        </div>
      </div>
    </S.ReviewEditForm>
  );
};

export default ReviewEditForm;
