import { css } from '@emotion/react';

import { REVIEW_LENGTH } from '@constants';

import type { Member, Noop, StudyId } from '@custom-types';

import { theme } from '@styles/theme';

import { usePostReview } from '@api/review';

import {
  type FieldElement,
  FormProvider,
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

export type ReviewFormProps = {
  studyId: StudyId;
  author: Member;
  onPostSuccess: Noop;
  onPostError: (e: Error) => void;
};

const REVIEW = 'review';

const ReviewForm: React.FC<ReviewFormProps> = ({ studyId, author, onPostSuccess, onPostError }) => {
  const { count, setCount, maxCount } = useLetterCount(REVIEW_LENGTH.MAX.VALUE);
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

  const handleReviewChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);

  return (
    <FormProvider {...formMethods}>
      <Card shadow backgroundColor={theme.colors.white} custom={{ padding: '8px' }}>
        <Form onSubmit={handleSubmit(onSubmit)}>
          <UserInfoItem src={author.imageUrl} name={author.username} size="sm">
            <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
          </UserInfoItem>
          <ReviewField isValid={isValid} handleChange={handleReviewChange} />
          <Divider space="4px" />
          <Flex justifyContent="space-between" alignItems="center">
            <LetterCounter count={count} maxCount={maxCount} />
            <PublishButton />
          </Flex>
        </Form>
      </Card>
    </FormProvider>
  );
};

export default ReviewForm;

type ReviewFieldProps = {
  isValid: boolean;
  handleChange: React.ChangeEventHandler<FieldElement>;
};
const ReviewField: React.FC<ReviewFieldProps> = ({ isValid, handleChange }) => {
  const { register } = useFormContext();
  return (
    <div
      css={css`
        padding-top: 10px;
        padding-bottom: 10px;
      `}
    >
      <Label htmlFor={REVIEW} hidden>
        스터디 후기
      </Label>
      <Textarea
        id={REVIEW}
        placeholder="스터디 후기를 작성해주세요."
        invalid={!isValid}
        border={false}
        {...register(REVIEW, {
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

const PublishButton: React.FC = () => (
  <BoxButton type="submit" fluid={false} custom={{ padding: '4px 10px', fontSize: 'sm' }}>
    등록
  </BoxButton>
);
