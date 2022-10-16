import { useParams } from 'react-router-dom';

import { css, useTheme } from '@emotion/react';
import styled from '@emotion/styled';

import { LINK_DESCRIPTION_LENGTH, LINK_URL_LENGTH } from '@constants';

import type { Member, Noop } from '@custom-types';

import { usePostLink } from '@api/link';

import {
  type FieldElement,
  type UseFormSubmitResult,
  makeValidationResult,
  useForm,
  useFormContext,
} from '@hooks/useForm';

import { BoxButton } from '@shared/button';
import Card from '@shared/card/Card';
import Flex from '@shared/flex/Flex';
import Form from '@shared/form/Form';
import Input from '@shared/input/Input';
import Label from '@shared/label/Label';
import ImportedLetterCounter, {
  type LetterCounterProps as ImportedLetterCounterProps,
} from '@shared/letter-counter/LetterCounter';
import useLetterCount from '@shared/letter-counter/useLetterCount';
import Textarea from '@shared/textarea/Textarea';
import UserInfoItem from '@shared/user-info-item/UserInfoItem';

export type LinkFormProps = {
  author: Member;
  onPostSuccess: Noop;
  onPostError: (error: Error) => void;
};

const LINK_URL = 'link-url';
const LINK_DESCRIPTION = 'link-description';

const LinkForm: React.FC<LinkFormProps> = ({ author, onPostSuccess, onPostError }) => {
  const theme = useTheme();
  const { studyId } = useParams<{ studyId: string }>();
  const { mutateAsync } = usePostLink();
  const { count, maxCount, setCount } = useLetterCount(LINK_DESCRIPTION_LENGTH.MAX.VALUE);
  const {
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
    <Self>
      <Card backgroundColor={theme.colors.white} custom={{ padding: '16px', gap: '12px' }}>
        <UserInfoItem size="sm" src={author.imageUrl} name={author.username}>
          <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
        </UserInfoItem>
        <Form onSubmit={handleSubmit(onSubmit)}>
          <Flex flexDirection="column" rowGap="12px">
            <LinkField isValid={isLinkValid} />
            <DescriptionField
              isValid={isDescValid}
              count={count}
              maxCount={maxCount}
              onChange={handleLinkDescriptionChange}
            />
            <AddLinkButton />
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
};
const LinkField: React.FC<LinkFieldProps> = ({ isValid }) => {
  const { register } = useFormContext();
  return (
    <>
      <Label htmlFor={LINK_URL}>링크*</Label>
      <Input
        type="url"
        id={LINK_URL}
        placeholder="https://moamoa.space"
        invalid={!isValid}
        fluid
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
};

type DescriptionFieldProps = {
  isValid: boolean;
  count: number;
  maxCount: number;
  onChange: React.ChangeEventHandler<FieldElement>;
};
const DescriptionField: React.FC<DescriptionFieldProps> = ({ isValid, count, maxCount, onChange: handleChange }) => {
  const { register } = useFormContext();
  return (
    <>
      <Label htmlFor={LINK_DESCRIPTION}>설명*</Label>
      <div
        css={css`
          position: relative;
          width: 100%;
        `}
      >
        <Textarea
          id={LINK_DESCRIPTION}
          placeholder="링크에 관한 간단한 설명"
          invalid={!isValid}
          fluid
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
};

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

const AddLinkButton: React.FC = () => (
  <BoxButton type="submit" custom={{ padding: '8px', fontSize: 'lg' }}>
    링크등록
  </BoxButton>
);

export default LinkForm;
