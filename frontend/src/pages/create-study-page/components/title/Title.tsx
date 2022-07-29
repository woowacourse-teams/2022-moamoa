import cn from 'classnames';
import { useState } from 'react';

import { css } from '@emotion/react';

import { TITLE_LENGTH } from '@constants';

import { makeValidationResult, useFormContext } from '@hooks/useForm';

import LetterCounter from '@components/letter-counter/LetterCounter';

const Title: React.FC = () => {
  const { register, formState } = useFormContext();
  const { errors } = formState;
  const [count, setCount] = useState(0);

  return (
    <div
      css={css`
        position: relative;
      `}
    >
      <div
        css={css`
          position: absolute;
          right: 4px;
          bottom: 2px;

          display: flex;
          justify-content: flex-end;
        `}
      >
        <LetterCounter count={count} maxCount={TITLE_LENGTH.MAX.VALUE} />
      </div>
      <input
        className={cn('title-input', { invalid: !!errors['title']?.hasError })}
        type="text"
        placeholder="스터디 이름"
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
        })}
      />
    </div>
  );
};

export default Title;
