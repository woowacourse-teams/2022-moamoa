import { useState } from 'react';

import { TITLE_LENGTH } from '@constants';

import { makeValidationResult, useFormContext } from '@hooks/useForm';

import LetterCounter from '@components/letter-counter/LetterCounter';

import * as S from './Title.style';

const Title: React.FC = () => {
  const { register, formState } = useFormContext();
  const { errors } = formState;
  const [count, setCount] = useState(0);
  const isValid = !!errors['title']?.hasError;

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
          onChange: e => setCount(e.target.value.length),
          minLength: TITLE_LENGTH.MIN.VALUE,
          maxLength: TITLE_LENGTH.MAX.VALUE,
          required: true,
        })}
      />
    </S.Container>
  );
};

export default Title;
