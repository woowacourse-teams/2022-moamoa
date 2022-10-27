import { css, useTheme } from '@emotion/react';

import { changeDateSeperator } from '@utils';

import type { DateYMD, Member } from '@custom-types';

import { type FieldElement, makeValidationResult, useFormContext } from '@hooks/useForm';

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

type CommentEditFormProps = {
  author: Member;
  maxLength: number;
  date: DateYMD;
  renderField: (setCount: (count: number) => void) => React.ReactNode;
  onSubmit: (_: React.FormEvent<HTMLFormElement>) => void;
  onCancelEditButtonClick: React.MouseEventHandler<HTMLButtonElement>;
};
const CommentEditForm: React.FC<CommentEditFormProps> = ({
  renderField,
  author,
  date,
  maxLength,
  onSubmit: handleSubmit,
  onCancelEditButtonClick: handleCancelEditButtonClick,
}) => {
  const theme = useTheme();
  const { count, setCount, maxCount } = useLetterCount(maxLength);

  return (
    <Card shadow backgroundColor={theme.colors.white} custom={{ padding: '8px' }}>
      <Form onSubmit={handleSubmit}>
        <UserInfoItem src={author.imageUrl} name={author.username} size="sm">
          <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
          <UserInfoItem.Content>{changeDateSeperator(date)}</UserInfoItem.Content>
        </UserInfoItem>
        {renderField(setCount)}
        <Divider space="4px" />
        <Flex justifyContent="space-between" alignItems="center">
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

type CommentEditFieldProps = {
  name: string;
  isValid: boolean;
  label: string;
  placeholder: string;
  defaultValue: string;
  minLength: number;
  minLengthErrorMessage: string;
  maxLength: number;
  maxLengthErrorMessage: string;
  onChange: React.ChangeEventHandler<FieldElement>;
};
const CommentEditField: React.FC<CommentEditFieldProps> = ({
  name,
  isValid,
  label,
  placeholder,
  defaultValue,
  minLength,
  minLengthErrorMessage,
  maxLength,
  maxLengthErrorMessage,
  onChange: handleChange,
}) => {
  const { register } = useFormContext();
  return (
    <div
      css={css`
        padding-top: 10px;
        padding-bottom: 10px;
      `}
    >
      <Label htmlFor={name} hidden>
        {label}
      </Label>
      <Textarea
        id={name}
        placeholder={placeholder}
        invalid={!isValid}
        border={false}
        defaultValue={defaultValue}
        {...register(name, {
          validate: (val: string) => {
            if (val.length < minLength) {
              return makeValidationResult(true, minLengthErrorMessage);
            }
            if (val.length > maxLength) return makeValidationResult(true, maxLengthErrorMessage);
            return makeValidationResult(false);
          },
          validationMode: 'change',
          onChange: handleChange,
          minLength: minLength,
          maxLength: maxLength,
          required: true,
        })}
      ></Textarea>
    </div>
  );
};

export default Object.assign(CommentEditForm, { CommentEditField });

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
