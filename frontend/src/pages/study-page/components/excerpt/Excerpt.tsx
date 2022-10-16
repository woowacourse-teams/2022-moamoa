import { css } from '@emotion/react';

import { EXCERPT_LENGTH } from '@constants';

import type { StudyDetail } from '@custom-types';

import { type FieldElement, type UseFormRegister, makeValidationResult, useForm, useFormContext } from '@hooks/useForm';

import Label from '@shared/label/Label';
import ImportedLetterCounter, {
  type LetterCounterProps as ImportedLetterCounterProps,
} from '@shared/letter-counter/LetterCounter';
import useLetterCount from '@shared/letter-counter/useLetterCount';
import MetaBox from '@shared/meta-box/MetaBox';
import Textarea, { type TextareaProps } from '@shared/textarea/Textarea';

export type ExcerptProps = {
  originalExcerpt?: StudyDetail['excerpt'];
};

const EXCERPT = 'excerpt';

const Excerpt = ({ originalExcerpt }: ExcerptProps) => {
  const {
    formState: { errors },
  } = useFormContext();

  const isValid = !errors[EXCERPT]?.hasError;

  const { count, setCount, maxCount } = useLetterCount(EXCERPT_LENGTH.MAX.VALUE, originalExcerpt?.length ?? 0);

  const handleExcerptChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);

  return (
    <div>
      <MetaBox>
        <MetaBox.Title>
          <Label htmlFor={EXCERPT}>한 줄 소개</Label>
        </MetaBox.Title>
        <MetaBox.Content>
          <div
            css={css`
              position: relative;
            `}
          >
            <LetterCounter count={count} maxCount={maxCount} />
            <ExceprtTextArea isValid={isValid} defaultValue={originalExcerpt ?? ''} onChange={handleExcerptChange} />
          </div>
        </MetaBox.Content>
      </MetaBox>
    </div>
  );
};

type LetterCouterProps = ImportedLetterCounterProps;
const LetterCounter: React.FC<LetterCouterProps> = ({ ...props }) => {
  const style = css`
    position: absolute;
    right: 6px;
    bottom: 6px;
  `;
  return (
    <div css={style}>
      <ImportedLetterCounter {...props} />
    </div>
  );
};

type ExcerptTextAreaProps = {
  isValid: boolean;
  defaultValue: string;
  onChange: TextareaProps['onChange'];
};
const ExceprtTextArea: React.FC<ExcerptTextAreaProps> = ({ isValid, defaultValue, onChange: handleChange }) => {
  const { register } = useFormContext();
  return (
    <Textarea
      id={EXCERPT}
      placeholder="*한줄소개를 입력해주세요"
      invalid={!isValid}
      defaultValue={defaultValue}
      {...register(EXCERPT, {
        validate: (val: string) => {
          if (val.length < EXCERPT_LENGTH.MIN.VALUE) {
            return makeValidationResult(true, EXCERPT_LENGTH.MIN.MESSAGE);
          }
          if (val.length > EXCERPT_LENGTH.MAX.VALUE) return makeValidationResult(true, EXCERPT_LENGTH.MAX.MESSAGE);
          return makeValidationResult(false);
        },
        validationMode: 'change',
        onChange: handleChange,
        minLength: EXCERPT_LENGTH.MIN.VALUE,
        maxLength: EXCERPT_LENGTH.MAX.VALUE,
        required: true,
      })}
    />
  );
};

export default Excerpt;
