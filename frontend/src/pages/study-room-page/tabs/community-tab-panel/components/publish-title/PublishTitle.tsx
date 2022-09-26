import { useState } from 'react';

import { TITLE_LENGTH } from '@constants';

import { makeValidationResult, useFormContext } from '@hooks/useForm';
import type { FieldElement } from '@hooks/useForm';

import LetterCounter from '@components/letter-counter/LetterCounter';

import * as S from '@community-tab/components/publish-title/PublishTitle.style';

const PublishTitle: React.FC = () => {
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
      <S.Input
        id="title"
        type="text"
        placeholder="제목을 입력해 주세요"
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
          onChange: handleTitleChange,
          minLength: TITLE_LENGTH.MIN.VALUE,
          maxLength: TITLE_LENGTH.MAX.VALUE,
          required: true,
        })}
      />
    </S.Container>
  );
};

export default PublishTitle;
