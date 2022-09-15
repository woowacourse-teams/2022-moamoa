import { REVIEW_LENGTH } from '@constants';

import { changeDateSeperator } from '@utils';
import tw from '@utils/tw';

import type { DateYMD, Member, Noop, ReviewId, StudyId } from '@custom-types';

import { usePutReview } from '@api/review';

import { makeValidationResult, useForm } from '@hooks/useForm';
import type { FieldElement, UseFormSubmitResult } from '@hooks/useForm';

import { BoxButton } from '@components/button';
import ButtonGroup from '@components/button-group/ButtonGroup';
import Card from '@components/card/Card';
import Divider from '@components/divider/Divider';
import Flex from '@components/flex/Flex';
import Form from '@components/form/Form';
import Label from '@components/label/Label';
import LetterCounter from '@components/letter-counter/LetterCounter';
import useLetterCount from '@components/letter-counter/useLetterCount';
import Textarea from '@components/textarea/Textarea';
import UserInfoItem from '@components/user-info-item/UserInfoItem';

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
  onCancelEditBtnClick,
}) => {
  const { count, setCount, maxCount } = useLetterCount(REVIEW_LENGTH.MAX.VALUE, originalContent.length);
  const {
    register,
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
    <Card shadow padding="8px" backgroundColor="#ffffff">
      <Form onSubmit={handleSubmit(onSubmit)}>
        <UserInfoItem src={author.imageUrl} name={author.username} size="sm">
          <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
          <UserInfoItem.Content>{changeDateSeperator(date)}</UserInfoItem.Content>
        </UserInfoItem>
        <div css={tw`py-10`}>
          <Label htmlFor={REVIEW_EDIT} hidden>
            스터디 후기
          </Label>
          <Textarea
            id={REVIEW_EDIT}
            placeholder="스터디 후기를 수정해주세요."
            invalid={!isValid}
            border={false}
            defaultValue={originalContent}
            {...register(REVIEW_EDIT, {
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
          <ButtonGroup gap="12px" width="fit-content">
            <BoxButton
              type="button"
              variant="secondary"
              fluid={false}
              padding="4px 10px"
              fontSize="sm"
              onClick={onCancelEditBtnClick}
            >
              취소
            </BoxButton>
            <BoxButton type="submit" fluid={false} padding="4px 10px" fontSize="sm">
              수정
            </BoxButton>
          </ButtonGroup>
        </Flex>
      </Form>
    </Card>
  );
};

export default ReviewEditForm;
