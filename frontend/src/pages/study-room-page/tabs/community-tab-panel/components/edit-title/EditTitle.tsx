import { useState } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { TITLE_LENGTH } from '@constants';

import tw from '@utils/tw';

import { UseFormRegister, makeValidationResult, useFormContext } from '@hooks/useForm';
import type { FieldElement } from '@hooks/useForm';

import Input from '@components/input/Input';
import Label from '@components/label/Label';
import ImportedLetterCounter, {
  type LetterCounterProps as ImportedLetterCounterProps,
} from '@components/letter-counter/LetterCounter';

export type EditTitleProps = {
  title: string;
};

const TITLE = 'title';

const EditTitle: React.FC<EditTitleProps> = ({ title }) => {
  const { register, formState } = useFormContext();
  const { errors } = formState;
  const [count, setCount] = useState(0);
  const isValid = !errors[TITLE]?.hasError;

  const handleTitleChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);

  return (
    <Self>
      <LetterCounter count={count} maxCount={TITLE_LENGTH.MAX.VALUE} />
      <TitleField isValid={isValid} title={title} onChange={handleTitleChange} register={register} />
    </Self>
  );
};

const Self = styled.div`
  position: relative;
`;

type LetterCouterProps = ImportedLetterCounterProps;
const LetterCounter: React.FC<LetterCouterProps> = ({ ...props }) => {
  const style = css`
    position: absolute;
    right: 4px;
    bottom: 2px;
  `;
  return (
    <div css={style}>
      <ImportedLetterCounter {...props} />
    </div>
  );
};

type TitleFieldProps = {
  isValid: boolean;
  title: string;
  register: UseFormRegister;
  onChange: React.ChangeEventHandler<HTMLInputElement>;
};
const TitleField: React.FC<TitleFieldProps> = ({ isValid, title, register, onChange: handleChange }) => (
  <>
    <Label htmlFor={TITLE} hidden>
      게시글 제목
    </Label>
    <div
      css={css`
        margin-bottom: 20px;
      `}
    >
      <Input
        id={TITLE}
        type="text"
        placeholder="제목을 입력해 주세요"
        invalid={!isValid}
        fluid
        fontSize="xl"
        defaultValue={title}
        {...register(TITLE, {
          validate: (val: string) => {
            if (val.length < TITLE_LENGTH.MIN.VALUE) {
              return makeValidationResult(true, TITLE_LENGTH.MIN.MESSAGE);
            }
            if (val.length > TITLE_LENGTH.MAX.VALUE) {
              return makeValidationResult(true, TITLE_LENGTH.MAX.MESSAGE);
            }
            return makeValidationResult(false);
          },
          validationMode: 'change',
          onChange: handleChange,
          minLength: TITLE_LENGTH.MIN.VALUE,
          maxLength: TITLE_LENGTH.MAX.VALUE,
          required: true,
        })}
      />
    </div>
  </>
);

export default EditTitle;
