import { css, useTheme } from '@emotion/react';

import { REVIEW_LENGTH } from '@constants';

import { changeDateSeperator } from '@utils';

import type { DateYMD, Member, Noop, ReviewId, StudyId } from '@custom-types';

import { usePutReview } from '@api/review';

import {
  type FieldElement,
  type UseFormRegister,
  type UseFormSubmitResult,
  makeValidationResult,
  useForm,
  useFormContext,
} from '@hooks/useForm';

import { BoxButton } from '@shared/button';
import Card from '@shared/card/Card';
import Divider from '@shared/divider/Divider';
import Flex from '@shared/flex/Flex';
import Form from '@shared/form/Form';
import Label from '@shared/label/Label';
import LetterCounter from '@shared/letter-counter/LetterCounter';
import useLetterCount from '@shared/letter-counter/useLetterCount';
import Textarea from '@shared/textarea/Textarea';
import UserInfoItem from '@shared/user-info-item/UserInfoItem';

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
  const theme = useTheme();
  const { count, setCount, maxCount } = useLetterCount(REVIEW_LENGTH.MAX.VALUE, originalContent.length);
  const {
    handleSubmit,
    formState: { errors },
  } = useForm();
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

  const handleReviewChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);

  return (
    <Card shadow backgroundColor={theme.colors.white} custom={{ padding: '8px' }}>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <UserInfoItem src={author.imageUrl} name={author.username} size="sm">
          <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
          <UserInfoItem.Content>{changeDateSeperator(date)}</UserInfoItem.Content>
        </UserInfoItem>
        <ReviewField isValid={isValid} defaultValue={originalContent} onChange={handleReviewChange} />
        <Divider space="4px" />
        <Flex justifyContent="space-between">
          <LetterCounter count={count} maxCount={maxCount} />
          <Flex columnGap="12px">
            <CancelButton onClick={handleCancelEditButtonClick} />
            <EditButton />
          </Flex>
        </Flex>
      </Form>
    </Card>
  );
};

type ReviewFieldProps = {
  isValid: boolean;
  defaultValue: string;
  onChange: React.ChangeEventHandler<FieldElement>;
};
const ReviewField: React.FC<ReviewFieldProps> = ({ isValid, defaultValue, onChange: handleChange }) => {
  const { register } = useFormContext();

  return (
    <div
      css={css`
        padding: 10px;
      `}
    >
      <Label htmlFor={REVIEW_EDIT} hidden>
        스터디 후기
      </Label>
      <Textarea
        id={REVIEW_EDIT}
        placeholder="스터디 후기를 수정해주세요."
        invalid={!isValid}
        border={false}
        defaultValue={defaultValue}
        {...register(REVIEW_EDIT, {
          validate: (val: string) => {
            if (val.length < REVIEW_LENGTH.MIN.VALUE) {
              return makeValidationResult(true, REVIEW_LENGTH.MIN.MESSAGE);
            }
            if (val.length > REVIEW_LENGTH.MAX.VALUE) return makeValidationResult(true, REVIEW_LENGTH.MAX.MESSAGE);
            return makeValidationResult(false);
          },
          validationMode: 'change',
          onChange: handleChange,
          minLength: REVIEW_LENGTH.MIN.VALUE,
          maxLength: REVIEW_LENGTH.MAX.VALUE,
          required: true,
        })}
      ></Textarea>
    </div>
  );
};

type CancelButtonProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const CancelButton: React.FC<CancelButtonProps> = ({ onClick: handleClick }) => (
  <BoxButton type="submit" fluid={false} onClick={handleClick} custom={{ padding: '4px 10px', fontSize: 'sm' }}>
    취소
  </BoxButton>
);

const EditButton: React.FC = () => (
  <BoxButton type="submit" variant="secondary" fluid={false} custom={{ padding: '4px 10px', fontSize: 'sm' }}>
    수정하기
  </BoxButton>
);

export default ReviewEditForm;
