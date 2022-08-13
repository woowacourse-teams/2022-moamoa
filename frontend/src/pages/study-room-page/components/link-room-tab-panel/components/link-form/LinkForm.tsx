import { LINK_DESCRIPTION_LENGTH, LINK_URL_LENGTH } from '@constants';

import tw from '@utils/tw';

import { UseFormSubmitResult, makeValidationResult, useForm } from '@hooks/useForm';

import Avatar from '@components/avatar/Avatar';
import Button from '@components/button/Button';
import LetterCounter from '@components/letter-counter/LetterCounter';
import useLetterCount from '@components/letter-counter/useLetterCount';

import * as S from '@study-room-page/components/link-room-tab-panel/components/link-form/LinkForm.style';

export type LinkFormProps = {};

const LINK_URL = 'link-url';
const LINK_DESCRIPTION = 'link-description';

const LinkForm: React.FC<LinkFormProps> = () => {
  const { count, maxCount, setCount } = useLetterCount(LINK_DESCRIPTION_LENGTH.MAX.VALUE);
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();

  const isLinkValid = !!errors[LINK_URL]?.hasError;
  const isDescValid = !!errors[LINK_DESCRIPTION]?.hasError;

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    if (!submitResult.values) {
      return;
    }

    const postData = {
      linkUrl: submitResult.values[LINK_URL],
      description: submitResult.values[LINK_DESCRIPTION],
    };

    // return mutateAsync(
    //   { studyId, content },
    //   {
    //     onSuccess: () => {
    //       reset(LINK_URL);
    //       reset(LINK_DESCRIPTION);
    //       onPostSuccess();
    //     },
    //     onError: error => {
    //       onPostError(error);
    //     },
    //   },
    // );
  };

  return (
    <S.LinkFormContainer>
      <S.AuthorInfoContainer>
        <Avatar size="xs" profileImg="" profileAlt="" />
        <S.AuthorName>person</S.AuthorName>
      </S.AuthorInfoContainer>
      <S.Form onSubmit={handleSubmit(onSubmit)}>
        <S.FormLabel htmlFor={LINK_URL}>링크*</S.FormLabel>
        <S.FormInput
          type="url"
          id={LINK_URL}
          placeholder="https://moamoa.space"
          isValid={isLinkValid}
          {...register(LINK_URL, {
            validate: (val: string) => {
              if (val.length < LINK_URL_LENGTH.MIN.VALUE) {
                return makeValidationResult(true, LINK_URL_LENGTH.MIN.MESSAGE);
              }
              if (val.length > LINK_URL_LENGTH.MAX.VALUE)
                return makeValidationResult(true, LINK_URL_LENGTH.MAX.MESSAGE);
              return makeValidationResult(false);
            },
            validationMode: 'change',
            maxLength: LINK_URL_LENGTH.MAX.VALUE,
            minLength: LINK_URL_LENGTH.MIN.VALUE,
            required: true,
          })}
        />
        <S.FormLabel htmlFor={LINK_DESCRIPTION}>설명*</S.FormLabel>
        <S.TextAreaContainer>
          <S.FormTextArea
            id={LINK_DESCRIPTION}
            placeholder="링크에 관한 간단한 설명"
            isValid={isDescValid}
            {...register(LINK_DESCRIPTION, {
              validate: (val: string) => {
                if (val.length < LINK_DESCRIPTION_LENGTH.MIN.VALUE) {
                  return makeValidationResult(true, LINK_DESCRIPTION_LENGTH.MIN.MESSAGE);
                }
                if (val.length > LINK_DESCRIPTION_LENGTH.MAX.VALUE)
                  return makeValidationResult(true, LINK_DESCRIPTION_LENGTH.MAX.MESSAGE);
                return makeValidationResult(false);
              },
              validationMode: 'change',
              onChange: e => setCount(e.target.value.length),
              maxLength: LINK_DESCRIPTION_LENGTH.MAX.VALUE,
              minLength: LINK_DESCRIPTION_LENGTH.MIN.VALUE,
              required: true,
            })}
          />
          <S.LetterCounterContainer>
            <LetterCounter count={count} maxCount={maxCount} />
          </S.LetterCounterContainer>
        </S.TextAreaContainer>
        <Button css={tw`p-8 text-16`}>링크 등록</Button>
      </S.Form>
    </S.LinkFormContainer>
  );
};

export default LinkForm;
