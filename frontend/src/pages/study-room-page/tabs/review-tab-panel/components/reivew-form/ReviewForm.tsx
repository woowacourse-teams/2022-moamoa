import { REVIEW_LENGTH } from '@constants';

import tw from '@utils/tw';

import type { Member, Noop, StudyId } from '@custom-types';

import { theme } from '@styles/theme';

import { usePostReview } from '@api/review';

import { makeValidationResult, useForm } from '@hooks/useForm';
import type { FieldElement, UseFormSubmitResult } from '@hooks/useForm';

import { BoxButton } from '@components/button';
import Card from '@components/card/Card';
import Divider from '@components/divider/Divider';
import Flex from '@components/flex/Flex';
import Form from '@components/form/Form';
import Label from '@components/label/Label';
import LetterCounter from '@components/letter-counter/LetterCounter';
import useLetterCount from '@components/letter-counter/useLetterCount';
import Textarea from '@components/textarea/Textarea';
import UserInfoItem from '@components/user-info-item/UserInfoItem';

export type ReviewFormProps = {
  studyId: StudyId;
  author: Member;
  onPostSuccess: Noop;
  onPostError: (e: Error) => void;
};

const REVIEW = 'review';

const ReviewForm: React.FC<ReviewFormProps> = ({ studyId, author, onPostSuccess, onPostError }) => {
  const { count, setCount, maxCount } = useLetterCount(REVIEW_LENGTH.MAX.VALUE);
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();
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
    <Card shadow padding="8px" backgroundColor={theme.colors.white}>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <UserInfoItem src={author.imageUrl} name={author.username} size="sm">
          <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
        </UserInfoItem>
        <div css={tw`py-10`}>
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
              onChange: handleReviewChange,
              minLength: REVIEW_LENGTH.MIN.VALUE,
              maxLength: REVIEW_LENGTH.MAX.VALUE,
              required: true,
            })}
          ></Textarea>
        </div>
        <Divider space="4px" />
        <Flex justifyContent="space-between" alignItems="center">
          <LetterCounter count={count} maxCount={maxCount} />
          <BoxButton type="submit" fluid={false} padding="4px 10px" fontSize="sm">
            등록
          </BoxButton>
        </Flex>
      </Form>
    </Card>
  );
};

export default ReviewForm;
