import { useState } from 'react';

import { TITLE_LENGTH } from '@constants';

import tw from '@utils/tw';

import { makeValidationResult, useFormContext } from '@hooks/useForm';
import type { FieldElement } from '@hooks/useForm';

import Input from '@components/input/Input';
import Label from '@components/label/Label';
import LetterCounter from '@components/letter-counter/LetterCounter';

export type EditTitleProps = {
  title: string;
};

const TITLE = 'title';

const EditTitle: React.FC<EditTitleProps> = ({ title }) => {
  const { register, formState } = useFormContext();
  const { errors } = formState;
  const [count, setCount] = useState(0);
  const isValid = !errors[TITLE]?.hasError;

  const handleTitleChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) => setCount(value.length);

  return (
    <div css={tw`relative`}>
      <div css={tw`absolute right-4 bottom-2`}>
        <LetterCounter count={count} maxCount={TITLE_LENGTH.MAX.VALUE} />
      </div>
      <Label htmlFor={TITLE} hidden>
        게시글 제목
      </Label>
      <div css={tw`mb-20`}>
        <Input
          id={TITLE}
          type="text"
          placeholder="제목을 입력해 주세요"
          invalid={!isValid}
          fluid
          fontSize="xl"
          defaultValue={title}
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

export default EditTitle;
