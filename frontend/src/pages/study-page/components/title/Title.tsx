import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { TITLE_LENGTH } from '@constants';

import tw from '@utils/tw';

import type { StudyDetail } from '@custom-types';

import { type FieldElement, UseFormRegister, makeValidationResult, useFormContext } from '@hooks/useForm';

import Input from '@components/input/Input';
import Label from '@components/label/Label';
import ImportedLetterCounter, {
  type LetterCounterProps as ImportedLetterCounterProps,
} from '@components/letter-counter/LetterCounter';
import useLetterCount from '@components/letter-counter/useLetterCount';

export type TitleProps = {
  originalTitle?: StudyDetail['title'];
};

const TITLE = 'title';

const Title: React.FC<TitleProps> = ({ originalTitle }) => {
  const { register, formState } = useFormContext();
  const { count, setCount, maxCount } = useLetterCount(TITLE_LENGTH.MAX.VALUE, originalTitle?.length ?? 0);

  const { errors } = formState;
  const isValid = !errors[TITLE]?.hasError;

  const handleTitleChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);

  return (
    <Self>
      <LetterCounter count={count} maxCount={maxCount} />
      <StudyNameField
        isValid={isValid}
        defaultValue={originalTitle ?? ''}
        onChange={handleTitleChange}
        register={register}
      />
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

type StudyNameFieldProps = {
  isValid: boolean;
  defaultValue: string;
  register: UseFormRegister;
  onChange: React.ChangeEventHandler<HTMLInputElement>;
};
const StudyNameField: React.FC<StudyNameFieldProps> = ({ isValid, defaultValue, register, onChange: handleChange }) => (
  <>
    <Label htmlFor={TITLE} hidden>
      스터디 이름
    </Label>
    <div css={tw`mb-20`}>
      <Input
        id={TITLE}
        type="text"
        placeholder="*스터디 이름"
        invalid={!isValid}
        fluid
        defaultValue={defaultValue}
        fontSize="xl"
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

export default Title;
