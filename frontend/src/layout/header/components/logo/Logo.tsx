import logoImage from '@assets/images/logo.png';

import * as S from '@layout/header/components/logo/Logo.style';

import Image from '@components/image/Image';

const Logo: React.FC = () => {
  return (
    <S.Row>
      <S.ImageContainer>
        <Image src={logoImage} alt="모아모아 로고 이미지" />
      </S.ImageContainer>
      <S.BorderText>MOAMOA</S.BorderText>
    </S.Row>
  );
};

export default Logo;
