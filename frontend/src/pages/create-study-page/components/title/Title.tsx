import { useState } from 'react';

import { TITLE_LENGTH } from '@constants';

import type { StudyDetail } from '@custom-types';

import { makeValidationResult, useFormContext } from '@hooks/useForm';
import type { FieldElement } from '@hooks/useForm';

import LetterCounter from '@components/letter-counter/LetterCounter';

import * as S from '@create-study-page/components/title/Title.style';

export type TitleProps = {
  originalTitle?: StudyDetail['title'];
};

const Title: React.FC<TitleProps> = ({ originalTitle }) => {
  const { register, formState } = useFormContext();
  const { errors } = formState;
  const [count, setCount] = useState(0);
  const isValid = !!errors['title']?.hasError;

  const handleTitleChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);

  return (
    <S.Container>
      <S.LetterCounterContainer>
        <LetterCounter count={count} maxCount={TITLE_LENGTH.MAX.VALUE} />
      </S.LetterCounterContainer>
      {/* TODO: HiddenLabel Component 생성 */}
      <S.Label htmlFor="title">스터디 이름</S.Label>
      <S.Input
        id="title"
        type="text"
        placeholder="*스터디 이름"
        isValid={isValid}
        defaultValue={originalTitle}
        {...register('title', {
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
          onChange: handleTitleChange,
          minLength: TITLE_LENGTH.MIN.VALUE,
          maxLength: TITLE_LENGTH.MAX.VALUE,
          required: true,
        })}
      />
    </S.Container>
  );
};

export default Title;
