import { REVIEW_LENGTH } from '@constants';

import tw from '@utils/tw';

import type { Member, Noop, StudyId } from '@custom-types';

import { usePostReview } from '@api/review';

import { makeValidationResult, useForm } from '@hooks/useForm';
import type { FieldElement, UseFormSubmitResult } from '@hooks/useForm';

import { BoxButton } from '@design/components/button';
import Card from '@design/components/card/Card';
import Divider from '@design/components/divider/Divider';
import Flex from '@design/components/flex/Flex';
import Form from '@design/components/form/Form';
import Item from '@design/components/item/Item';
import Label from '@design/components/label/Label';
import LetterCounter from '@design/components/letter-counter/LetterCounter';
import useLetterCount from '@design/components/letter-counter/useLetterCount';
import Textarea from '@design/components/textarea/Textarea';

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

  const isReviewValid = !errors[REVIEW]?.hasError;

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
    <Card shadow padding="8px" backgroundColor="#ffffff">
      <Form onSubmit={handleSubmit(onSubmit)}>
        <Item src={author.imageUrl} name={author.username} size="sm">
          <Item.Heading>{author.username}</Item.Heading>
        </Item>
        <div css={tw`py-10`}>
          <Label htmlFor={REVIEW} hidden>
            스터디 후기
          </Label>
          <Textarea
            id={REVIEW}
            placeholder="스터디 후기를 작성해주세요."
            invalid={!isReviewValid}
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
