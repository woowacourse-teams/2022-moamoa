import { EXCERPT_LENGTH } from '@constants';

import { makeValidationResult, useFormContext } from '@hooks/useForm';
import type { FieldElement } from '@hooks/useForm';

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

  const handleExcerptChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);

  return (
    <S.Excerpt className={className}>
      <MetaBox>
        <MetaBox.Title>한줄소개</MetaBox.Title>
        <MetaBox.Content>
          <S.Container>
            <S.LetterCounterContainer>
              <LetterCounter count={count} maxCount={maxCount} />
            </S.LetterCounterContainer>
            {/* TODO: HiddenLabel Component 생성 */}
            <S.Label htmlFor="excerpt">소개글</S.Label>
            <S.Textarea
              id="excerpt"
              placeholder="*한줄소개를 입력해주세요"
              isValid={!!errors['excerpt']?.hasError}
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
                onChange: handleExcerptChange,
                minLength: EXCERPT_LENGTH.MIN.VALUE,
                maxLength: EXCERPT_LENGTH.MAX.VALUE,
                required: true,
              })}
            />
          </S.Container>
        </MetaBox.Content>
      </MetaBox>
    </S.Excerpt>
  );
};

export default Excerpt;
