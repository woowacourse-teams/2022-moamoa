import type { AxiosError } from 'axios';
import { useMutation } from 'react-query';
import { useParams } from 'react-router-dom';

import { LINK_DESCRIPTION_LENGTH, LINK_URL_LENGTH } from '@constants';

import tw from '@utils/tw';

import type { Link, LinkId, Member, Noop, PutLinkRequestVariables } from '@custom-types';

import { putLink } from '@api';

import { makeValidationResult, useForm } from '@hooks/useForm';
import type { FieldElement, UseFormSubmitResult } from '@hooks/useForm';

import Avatar from '@components/avatar/Avatar';
import Button from '@components/button/Button';
import LetterCounter from '@components/letter-counter/LetterCounter';
import useLetterCount from '@components/letter-counter/useLetterCount';

import * as S from '@study-room-page/components/link-room-tab-panel/components/link-edit-form/LinkEditForm.style';

export type LinkFormProps = {
  linkId: LinkId;
  author: Member;
  originalContent: Pick<Link, 'linkUrl' | 'description'>;
  onPutSuccess: Noop;
  onPutError: (error: Error) => void;
};

const LINK_URL = 'link-url';
const LINK_DESCRIPTION = 'link-description';

const LinkEditForm: React.FC<LinkFormProps> = ({ author, linkId, originalContent, onPutSuccess, onPutError }) => {
  const { studyId } = useParams<{ studyId: string }>();
  const { mutateAsync } = useMutation<null, AxiosError, PutLinkRequestVariables>(putLink);
  const { count, maxCount, setCount } = useLetterCount(
    LINK_DESCRIPTION_LENGTH.MAX.VALUE,
    originalContent.description.length,
  );
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const isLinkValid = !!errors[LINK_URL]?.hasError;
  const isDescValid = !!errors[LINK_DESCRIPTION]?.hasError;

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    if (!submitResult.values) {
      return;
    }

    const putData = {
      studyId: Number(studyId),
      linkId,
      linkUrl: submitResult.values[LINK_URL],
      description: submitResult.values[LINK_DESCRIPTION],
    };

    return mutateAsync(putData, {
      onSuccess: () => {
        onPutSuccess();
      },
      onError: error => {
        onPutError(error);
      },
    });
  };

  const handleLinkDescriptionChange = ({ target: { value } }: React.ChangeEvent<FieldElement>) =>
    setCount(value.length);

  return (
    <S.LinkFormContainer>
      <S.AuthorInfoContainer>
        <Avatar size="xs" profileImg={author.imageUrl} profileAlt={`${author.username} 프로필`} />
        <S.AuthorName>person</S.AuthorName>
      </S.AuthorInfoContainer>
      <S.Form onSubmit={handleSubmit(onSubmit)}>
        <S.FormLabel htmlFor={LINK_URL}>링크*</S.FormLabel>
        <S.FormInput
          type="url"
          id={LINK_URL}
          placeholder="https://moamoa.space"
          isValid={isLinkValid}
          defaultValue={originalContent.linkUrl}
          {...register(LINK_URL, {
            validate: (val: string) => {
              if (val.length < LINK_URL_LENGTH.MIN.VALUE) {
                return makeValidationResult(true, LINK_URL_LENGTH.MIN.MESSAGE);
              }
              if (val.length > LINK_URL_LENGTH.MAX.VALUE)
                return makeValidationResult(true, LINK_URL_LENGTH.MAX.MESSAGE);
              if (!LINK_URL_LENGTH.FORMAT.TEST(val)) return makeValidationResult(true, LINK_URL_LENGTH.FORMAT.MESSAGE);
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
            defaultValue={originalContent.description}
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
              onChange: handleLinkDescriptionChange,
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

export default LinkEditForm;
