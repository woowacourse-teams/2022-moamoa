import { EXCERPT_LENGTH } from '@constants';

import tw from '@utils/tw';

import type { StudyDetail } from '@custom-types';

import { makeValidationResult, useFormContext } from '@hooks/useForm';
import type { FieldElement } from '@hooks/useForm';

import Label from '@components/label/Label';
import LetterCounter from '@components/letter-counter/LetterCounter';
import useLetterCount from '@components/letter-counter/useLetterCount';
import MetaBox from '@components/meta-box/MetaBox';
import Textarea from '@components/textarea/Textarea';

export type ExcerptProps = {
  originalExcerpt?: StudyDetail['description'];
};

const EXCERPT = 'excerpt';

const Excerpt = ({ originalExcerpt }: ExcerptProps) => {
  const {
    formState: { errors },
    register,
  } = useFormContext();

  const isExcerptValid = !errors[EXCERPT]?.hasError;

  const { count, setCount, maxCount } = useLetterCount(EXCERPT_LENGTH.MAX.VALUE, originalExcerpt?.length ?? 0);

  const handleExcerptChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);

  return (
    <div>
      <MetaBox>
        <MetaBox.Title>
          <Label htmlFor={EXCERPT}>한 줄 소개</Label>
        </MetaBox.Title>
        <MetaBox.Content>
          <div css={tw`relative`}>
            <div css={tw`absolute right-6 bottom-6`}>
              <LetterCounter count={count} maxCount={maxCount} />
            </div>
            <Textarea
              id={EXCERPT}
              placeholder="*한줄소개를 입력해주세요"
              invalid={!isExcerptValid}
              defaultValue={originalExcerpt}
              {...register(EXCERPT, {
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
          </div>
        </MetaBox.Content>
      </MetaBox>
    </div>
  );
};

export default Excerpt;
