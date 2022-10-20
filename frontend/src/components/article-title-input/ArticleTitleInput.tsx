import { useState } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { TITLE, TITLE_LENGTH } from '@constants';

import { type FieldElement, makeValidationResult, useFormContext } from '@hooks/useForm';

import Input from '@shared/input/Input';
import Label from '@shared/label/Label';
import ImportedLetterCounter, {
  type LetterCounterProps as ImportedLetterCounterProps,
} from '@shared/letter-counter/LetterCounter';

export type ArticleTitleInputProps = {
  originalTitle?: string;
};

const ArticleTitleInput: React.FC<ArticleTitleInputProps> = ({ originalTitle }) => {
  const [count, setCount] = useState(originalTitle ? originalTitle.length : 0);

  const handleTitleChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);

  return (
    <Self>
      <LetterCounter count={count} maxCount={TITLE_LENGTH.MAX.VALUE} />
      <TitleField title={originalTitle ?? ''} onChange={handleTitleChange} />
    </Self>
  );
};

export default ArticleTitleInput;

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
  title: string;
  onChange: React.ChangeEventHandler<HTMLInputElement>;
};

const TitleField: React.FC<TitleFieldProps> = ({ title, onChange: handleChange }) => {
  const {
    register,
    formState: { errors },
  } = useFormContext();

  const isValid = !errors[TITLE]?.hasError;

  return (
    <>
      <Label htmlFor={TITLE} hidden>
        공지사항 제목
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
};
