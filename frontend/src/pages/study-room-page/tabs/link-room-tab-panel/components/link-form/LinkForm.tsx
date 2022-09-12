import { useParams } from 'react-router-dom';

import { LINK_DESCRIPTION_LENGTH, LINK_URL_LENGTH } from '@constants';

import tw from '@utils/tw';

import type { Member, Noop } from '@custom-types';

import { usePostLink } from '@api/link';

import { UseFormSubmitResult, makeValidationResult, useForm } from '@hooks/useForm';
import type { FieldElement } from '@hooks/useForm';

import { BoxButton } from '@design/components/button';
import Card from '@design/components/card/Card';
import Flex from '@design/components/flex/Flex';
import Form from '@design/components/form/Form';
import Input from '@design/components/input/Input';
import Item from '@design/components/item/Item';
import Label from '@design/components/label/Label';
import LetterCounter from '@design/components/letter-counter/LetterCounter';
import useLetterCount from '@design/components/letter-counter/useLetterCount';
import Textarea from '@design/components/textarea/Textarea';

export type LinkFormProps = {
  author: Member;
  onPostSuccess: Noop;
  onPostError: (error: Error) => void;
};

const LINK_URL = 'link-url';
const LINK_DESCRIPTION = 'link-description';

const LinkForm: React.FC<LinkFormProps> = ({ author, onPostSuccess, onPostError }) => {
  const { studyId } = useParams<{ studyId: string }>();
  const { mutateAsync } = usePostLink();
  const { count, maxCount, setCount } = useLetterCount(LINK_DESCRIPTION_LENGTH.MAX.VALUE);
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const isLinkValid = !errors[LINK_URL]?.hasError;
  const isDescValid = !errors[LINK_DESCRIPTION]?.hasError;

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    if (!submitResult.values) {
      return;
    }

    const postData = {
      studyId: Number(studyId),
      linkUrl: submitResult.values[LINK_URL],
      description: submitResult.values[LINK_DESCRIPTION] || author.username,
    };

    return mutateAsync(postData, {
      onSuccess: () => {
        onPostSuccess();
      },
      onError: error => {
        onPostError(error);
      },
    });
  };

  const handleLinkDescriptionChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) =>
    setCount(value.length);

  return (
    <div css={tw`w-480 h-300`}>
      <Card backgroundColor="#ffffff" padding="16px" gap="12px">
        <Item size="sm" src={author.imageUrl} name={author.username}>
          <Item.Heading>{author.username}</Item.Heading>
        </Item>
        <Form onSubmit={handleSubmit(onSubmit)}>
          <Flex direction="column" rowGap="12px">
            <Label htmlFor={LINK_URL}>링크*</Label>
            <Input
              type="url"
              id={LINK_URL}
              placeholder="https://moamoa.space"
              invalid={!isLinkValid}
              fluid
              {...register(LINK_URL, {
                validate: (val: string) => {
                  if (val.length < LINK_URL_LENGTH.MIN.VALUE) {
                    return makeValidationResult(true, LINK_URL_LENGTH.MIN.MESSAGE);
                  }
                  if (val.length > LINK_URL_LENGTH.MAX.VALUE)
                    return makeValidationResult(true, LINK_URL_LENGTH.MAX.MESSAGE);
                  if (!LINK_URL_LENGTH.FORMAT.TEST(val))
                    return makeValidationResult(true, LINK_URL_LENGTH.FORMAT.MESSAGE);
                  return makeValidationResult(false);
                },
                validationMode: 'change',
                maxLength: LINK_URL_LENGTH.MAX.VALUE,
                minLength: LINK_URL_LENGTH.MIN.VALUE,
                required: true,
              })}
            />
            <Label htmlFor={LINK_DESCRIPTION}>설명*</Label>
            <div css={tw`relative`}>
              <Textarea
                id={LINK_DESCRIPTION}
                placeholder="링크에 관한 간단한 설명"
                invalid={!isDescValid}
                fluid
                {...register(LINK_DESCRIPTION, {
                  validate: (val: string) => {
                    if (val.length > LINK_DESCRIPTION_LENGTH.MAX.VALUE)
                      return makeValidationResult(true, LINK_DESCRIPTION_LENGTH.MAX.MESSAGE);
                    return makeValidationResult(false);
                  },
                  validationMode: 'change',
                  onChange: handleLinkDescriptionChange,
                  maxLength: LINK_DESCRIPTION_LENGTH.MAX.VALUE,
                })}
              />
              <div css={tw`absolute bottom-8 right-6`}>
                <LetterCounter count={count} maxCount={maxCount} />
              </div>
            </div>
            <BoxButton type="submit" padding="8px">
              링크 등록
            </BoxButton>
          </Flex>
        </Form>
      </Card>
    </div>
  );
};

export default LinkForm;
