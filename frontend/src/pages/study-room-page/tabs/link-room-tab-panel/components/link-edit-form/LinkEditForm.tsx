import { useParams } from 'react-router-dom';

import { Theme, css, useTheme } from '@emotion/react';
import styled from '@emotion/styled';

import { LINK_DESCRIPTION_LENGTH, LINK_URL_LENGTH } from '@constants';

import type { Link, LinkId, Member, Noop } from '@custom-types';

import { usePutLink } from '@api/link';

import { UseFormRegister, makeValidationResult, useForm } from '@hooks/useForm';
import type { FieldElement, UseFormSubmitResult } from '@hooks/useForm';

import { BoxButton } from '@components/button';
import Card from '@components/card/Card';
import Flex from '@components/flex/Flex';
import Form from '@components/form/Form';
import Input from '@components/input/Input';
import Label from '@components/label/Label';
import ImportedLetterCounter, {
  type LetterCounterProps as ImportedLetterCounterProps,
} from '@components/letter-counter/LetterCounter';
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
  const theme = useTheme();
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
    <Self>
      <Card backgroundColor={theme.colors.white} custom={{ padding: '16px', gap: '12px' }}>
        <UserInfoItem size="sm" src={author.imageUrl} name={author.username}>
          <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
        </UserInfoItem>
        <Form onSubmit={handleSubmit(onSubmit)}>
          <Flex flexDirection="column" rowGap="12px">
            <LinkField isValid={isLinkValid} linkUrl={originalContent.linkUrl} register={register} />
            <DescriptionField
              isValid={isDescValid}
              description={originalContent.description}
              count={count}
              maxCount={maxCount}
              onChange={handleLinkDescriptionChange}
              register={register}
            />
            <EditLinkButton theme={theme} />
          </Flex>
        </Form>
      </Card>
    </Self>
  );
};

const Self = styled.div`
  width: 480px;
  height: 300px;
`;

type LinkFieldProps = {
  isValid: boolean;
  linkUrl: string;
  register: UseFormRegister;
};
const LinkField: React.FC<LinkFieldProps> = ({ isValid, linkUrl, register }) => (
  <>
    <Label htmlFor={LINK_URL}>링크*</Label>
    <Input
      type="url"
      id={LINK_URL}
      placeholder="https://moamoa.space"
      invalid={!isValid}
      fluid
      defaultValue={linkUrl}
      {...register(LINK_URL, {
        validate: (val: string) => {
          if (val.length < LINK_URL_LENGTH.MIN.VALUE) {
            return makeValidationResult(true, LINK_URL_LENGTH.MIN.MESSAGE);
          }
          if (val.length > LINK_URL_LENGTH.MAX.VALUE) return makeValidationResult(true, LINK_URL_LENGTH.MAX.MESSAGE);
          if (!LINK_URL_LENGTH.FORMAT.TEST(val)) return makeValidationResult(true, LINK_URL_LENGTH.FORMAT.MESSAGE);
          return makeValidationResult(false);
        },
        validationMode: 'change',
        maxLength: LINK_URL_LENGTH.MAX.VALUE,
        minLength: LINK_URL_LENGTH.MIN.VALUE,
        required: true,
      })}
    />
  </>
);

type DescriptionFieldProps = {
  isValid: boolean;
  description: string;
  count: number;
  maxCount: number;
  onChange: React.ChangeEventHandler<FieldElement>;
  register: UseFormRegister;
};
const DescriptionField: React.FC<DescriptionFieldProps> = ({
  isValid,
  description,
  count,
  maxCount,
  onChange: handleChange,
  register,
}) => (
  <>
    <Label htmlFor={LINK_DESCRIPTION}>설명*</Label>
    <div
      css={css`
        position: relative;
      `}
    >
      <Textarea
        id={LINK_DESCRIPTION}
        placeholder="링크에 관한 간단한 설명"
        invalid={!isValid}
        fluid
        defaultValue={description}
        {...register(LINK_DESCRIPTION, {
          validate: (val: string) => {
            if (val.length > LINK_DESCRIPTION_LENGTH.MAX.VALUE)
              return makeValidationResult(true, LINK_DESCRIPTION_LENGTH.MAX.MESSAGE);
            return makeValidationResult(false);
          },
          validationMode: 'change',
          onChange: handleChange,
          maxLength: LINK_DESCRIPTION_LENGTH.MAX.VALUE,
        })}
      />
      <LetterCounter count={count} maxCount={maxCount} />
    </div>
  </>
);

type LetterCouterProps = ImportedLetterCounterProps;
const LetterCounter: React.FC<LetterCouterProps> = ({ ...props }) => {
  const style = css`
    position: absolute;
    right: 6px;
    bottom: 8px;
  `;
  return (
    <div css={style}>
      <ImportedLetterCounter {...props} />
    </div>
  );
};

type EditLinkButtonProps = {
  theme: Theme;
};
const EditLinkButton: React.FC<EditLinkButtonProps> = ({ theme }) => (
  <BoxButton type="submit" custom={{ padding: '8px', fontSize: theme.fontSize.lg }}>
    링크 수정
  </BoxButton>
);

export default LinkEditForm;
