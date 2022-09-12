import { TITLE_LENGTH } from '@constants';

import tw from '@utils/tw';

import type { StudyDetail } from '@custom-types';

import { type FieldElement, makeValidationResult, useFormContext } from '@hooks/useForm';

import Input from '@design/components/input/Input';
import Label from '@design/components/label/Label';
import LetterCounter from '@design/components/letter-counter/LetterCounter';
import useLetterCount from '@design/components/letter-counter/useLetterCount';

export type TitleProps = {
  originalTitle?: StudyDetail['title'];
};

const TITLE = 'title';

const Title: React.FC<TitleProps> = ({ originalTitle }) => {
  const { register, formState } = useFormContext();
  const { count, setCount, maxCount } = useLetterCount(TITLE_LENGTH.MAX.VALUE, originalTitle?.length ?? 0);

  const { errors } = formState;
  const isTitleValid = !errors[TITLE]?.hasError;

  const handleTitleChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);

  return (
    <div css={tw`relative`}>
      <div css={tw`absolute right-4 bottom-2`}>
        <LetterCounter count={count} maxCount={maxCount} />
      </div>
      <Label htmlFor={TITLE} hidden>
        스터디 이름
      </Label>
      <div css={tw`mb-20`}>
        <Input
          id={TITLE}
          type="text"
          placeholder="*스터디 이름"
          invalid={!isTitleValid}
          fluid
          defaultValue={originalTitle}
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
            onChange: handleTitleChange,
            minLength: TITLE_LENGTH.MIN.VALUE,
            maxLength: TITLE_LENGTH.MAX.VALUE,
            required: true,
          })}
        />
      </div>
    </div>
  );
};

export default Title;
