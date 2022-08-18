import { REVIEW_LENGTH } from '@constants';

import { changeDateSeperator } from '@utils';

import type { DateYMD, Member, Noop, ReviewId, StudyId } from '@custom-types';

import { usePutReview } from '@api/review';

import { makeValidationResult, useForm } from '@hooks/useForm';
import type { FieldElement, UseFormSubmitResult } from '@hooks/useForm';

import Avatar from '@components/avatar/Avatar';
import Button from '@components/button/Button';
import LetterCounter from '@components/letter-counter/LetterCounter';
import useLetterCount from '@components/letter-counter/useLetterCount';

import * as S from '@study-room-page/tabs/review-tab-panel/components/review-edit-form/ReviewEditForm.style';

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
  const { mutateAsync } = usePutReview();

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

  const handleReviewChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);

  return (
    <S.ReviewEditForm onSubmit={handleSubmit(onSubmit)}>
      <S.ReviewFormHead>
        <S.UserInfo>
          <S.AvatarLink href={author.profileUrl}>
            <Avatar profileImg={author.imageUrl} profileAlt="EMPTY" size="sm" />
          </S.AvatarLink>
          <S.UsernameContainer>
            <S.UsernameLink href={author.profileUrl}>{author.username}</S.UsernameLink>
            <S.Date>{changeDateSeperator(date)}</S.Date>
          </S.UsernameContainer>
        </S.UserInfo>
      </S.ReviewFormHead>
      <S.ReviewEditFormBody>
        <S.Textarea
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
            onChange: handleReviewChange,
            minLength: REVIEW_LENGTH.MIN.VALUE,
            maxLength: REVIEW_LENGTH.MAX.VALUE,
          })}
        ></S.Textarea>
      </S.ReviewEditFormBody>
      <S.ReviewEditFormFooter>
        <LetterCounter count={count} maxCount={maxCount} />
        <S.ReviewEditFormFooterButtonGroup variation="flex-end">
          <S.CancelButton type="button" onClick={onCancelEditBtnClick}>
            취소
          </S.CancelButton>
          <Button>수정</Button>
        </S.ReviewEditFormFooterButtonGroup>
      </S.ReviewEditFormFooter>
    </S.ReviewEditForm>
  );
};

export default ReviewEditForm;