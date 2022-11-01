import { css, useTheme } from '@emotion/react';

import type { Member } from '@custom-types';

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

type CommentFormProps = {
  author: Member;
  maxLength: number;
  renderField: (setCount: (count: number) => void) => React.ReactNode;
  onSubmit: (_: React.FormEvent<HTMLFormElement>) => void;
};
const CommentForm: React.FC<CommentFormProps> = ({ renderField, author, maxLength, onSubmit }) => {
  const theme = useTheme();
  const { count, setCount, maxCount } = useLetterCount(maxLength);
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    onSubmit(e);
    setCount(0);
  };

  return (
    <Card shadow backgroundColor={theme.colors.white} custom={{ padding: '8px' }}>
      <Form onSubmit={handleSubmit}>
        <UserInfoItem src={author.imageUrl} name={author.username} size="sm">
          <UserInfoItem.Heading>{author.username}</UserInfoItem.Heading>
        </UserInfoItem>
        {renderField(setCount)}
        <Divider space="4px" />
        <Flex justifyContent="space-between" alignItems="center">
          <LetterCounter count={count} maxCount={maxCount} />
          <PublishButton />
        </Flex>
      </Form>
    </Card>
  );
};

type CommentFieldProps = {
  name: string;
  isValid: boolean;
  label: string;
  placeholder: string;
  minLength: number;
  minLengthErrorMessage: string;
  maxLength: number;
  maxLengthErrorMessage: string;
  onChange: React.ChangeEventHandler<FieldElement>;
};
const CommentField: React.FC<CommentFieldProps> = ({
  name,
  isValid,
  label,
  placeholder,
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

export default Object.assign(CommentForm, { CommentField });

const PublishButton: React.FC = () => (
  <BoxButton type="submit" fluid={false} custom={{ padding: '4px 10px', fontSize: 'sm' }}>
    등록
  </BoxButton>
);
