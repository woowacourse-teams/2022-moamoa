import cn from 'classnames';

import { css } from '@emotion/react';

import { EXCERPT_LENGTH } from '@constants';

import { makeValidationResult, useFormContext } from '@hooks/useForm';

import LetterCounter from '@components/letter-counter/LetterCounter';
import useLetterCount from '@components/letter-counter/useLetterCount';

import * as S from '@create-study-page/components/excerpt/Excerpt.style';
import MetaBox from '@create-study-page/components/meta-box/MetaBox';

type ExcerptProps = {
  className?: string;
};

const Excerpt = ({ className }: ExcerptProps) => {
  const {
    formState: { errors },
    register,
  } = useFormContext();

  const { count, setCount, maxCount } = useLetterCount(EXCERPT_LENGTH.MAX.VALUE);

  return (
    <S.Excerpt className={className}>
      <MetaBox>
        <MetaBox.Title>한줄소개</MetaBox.Title>
        <MetaBox.Content>
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
              <LetterCounter count={count} maxCount={maxCount} />
            </div>
            <textarea
              placeholder="한줄소개를 입력해주세요"
              className={cn({ invalid: !!errors['excerpt']?.hasError })}
              {...register('excerpt', {
                validate: (val: string) => {
                  if (val.length < EXCERPT_LENGTH.MIN.VALUE) {
                    return makeValidationResult(true, EXCERPT_LENGTH.MIN.MESSAGE);
                  }
                  if (val.length > EXCERPT_LENGTH.MAX.VALUE)
                    return makeValidationResult(true, EXCERPT_LENGTH.MAX.MESSAGE);
                  return makeValidationResult(false);
                },
                validationMode: 'change',
                onChange: e => setCount(e.target.value.length),
                minLength: EXCERPT_LENGTH.MIN.VALUE,
                maxLength: EXCERPT_LENGTH.MAX.VALUE,
              })}
            />
          </div>
        </MetaBox.Content>
      </MetaBox>
    </S.Excerpt>
  );
};

export default Excerpt;
