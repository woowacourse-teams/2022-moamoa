import { useParams } from 'react-router-dom';

import { LINK_DESCRIPTION_LENGTH, LINK_URL_LENGTH } from '@constants';

import tw from '@utils/tw';

import type { Link, LinkId, Member, Noop } from '@custom-types';

import { theme } from '@styles/theme';

import { usePutLink } from '@api/link';

import { makeValidationResult, useForm } from '@hooks/useForm';
import type { FieldElement, UseFormSubmitResult } from '@hooks/useForm';

import { BoxButton } from '@components/button';
import Card from '@components/card/Card';
import Flex from '@components/flex/Flex';
import Form from '@components/form/Form';
import Input from '@components/input/Input';
import Label from '@components/label/Label';
import LetterCounter from '@components/letter-counter/LetterCounter';
import useLetterCount from '@components/letter-counter/useLetterCount';
import Textarea from '@components/textarea/Textarea';
import UserInfoItem from '@components/user-info-item/UserInfoItem';

export type LinkEditFormProps = {
  linkId: LinkId;
  author: Member;
  originalContent: Pick<Link, 'linkUrl' | 'description'>;
  onPutSuccess: Noop;
  onPutError: (error: Error) => void;
};

const LINK_URL = 'link-url';
const LINK_DESCRIPTION = 'link-description';

const LinkEditForm: React.FC<LinkEditFormProps> = ({ author, linkId, originalContent, onPutSuccess, onPutError }) => {
  const { studyId } = useParams<{ studyId: string }>();
  const { mutateAsync } = usePutLink();
  const { count, maxCount, setCount } = useLetterCount(
    LINK_DESCRIPTION_LENGTH.MAX.VALUE,
    originalContent.description.length,
  );
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

    const putData = {
      studyId: Number(studyId),
      linkId,
      linkUrl: submitResult.values[LINK_URL],
      description: submitResult.values[LINK_DESCRIPTION] || author.username,
    };

    return mutateAsync(putData, {
      onSuccess: () => {
        onPutSuccess();
      },
      onError: error => {
        onPutError(error);
      },
    });
  };

  const handleLinkDescriptionChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) =>
    setCount(value.length);

  return (
    <div css={tw`w-480 h-300`}>
      <Card backgroundColor={theme.colors.white} padding="16px" gap="12px">
        <UserInfoItem size="sm" src={author.imageUrl} name={author.username}>
          <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
        </UserInfoItem>
        <Form onSubmit={handleSubmit(onSubmit)}>
          <Flex direction="column" rowGap="12px">
            <Label htmlFor={LINK_URL}>링크*</Label>
            <Input
              type="url"
              id={LINK_URL}
              placeholder="https://moamoa.space"
              invalid={!isLinkValid}
              fluid
              defaultValue={originalContent.linkUrl}
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
                defaultValue={originalContent.description}
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
            <BoxButton type="submit" padding="8px" fontSize="lg">
              링크 등록
            </BoxButton>
          </Flex>
        </Form>
      </Card>
    </div>
  );
};

export default LinkEditForm;
