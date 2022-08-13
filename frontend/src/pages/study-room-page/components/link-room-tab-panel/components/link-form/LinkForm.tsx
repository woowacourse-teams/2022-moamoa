import { LINK_DESCRIPTION_LENGTH } from '@constants';

import tw from '@utils/tw';

import Avatar from '@components/avatar/Avatar';
import Button from '@components/button/Button';
import LetterCounter from '@components/letter-counter/LetterCounter';

import * as S from '@study-room-page/components/link-room-tab-panel/components/link-form/LinkForm.style';

export type LinkFormProps = {};

const LinkForm: React.FC<LinkFormProps> = () => {
  return (
    <S.LinkFormContainer>
      <S.AuthorInfoContainer>
        <Avatar size="xs" profileImg="" profileAlt="" />
        <S.AuthorName>person</S.AuthorName>
      </S.AuthorInfoContainer>
      <S.Form>
        <S.FormLabel htmlFor="link">링크</S.FormLabel>
        <S.FormInput type="text" id="link" placeholder="https://moamoa.space" />
        <S.FormLabel htmlFor="description">설명</S.FormLabel>
        <S.TextAreaContainer>
          <S.FormTextArea id="description" placeholder="링크에 관한 간단한 설명" />
          <S.LetterCounterContainer>
            <LetterCounter count={5} maxCount={LINK_DESCRIPTION_LENGTH.MAX.VALUE} />
          </S.LetterCounterContainer>
        </S.TextAreaContainer>
        <Button css={tw`p-8 text-16`}>링크 등록</Button>
      </S.Form>
    </S.LinkFormContainer>
  );
};

export default LinkForm;
