import logoImage from '@assets/images/logo.png';

import * as S from '@layout/header/components/logo/Logo.style';

import Flex from '@design/components/flex/Flex';
import Image from '@design/components/image/Image';

const Logo: React.FC = () => {
  return (
    <Flex justifyContent="center" alignItems="center">
      <S.ImageContainer>
        <Image src={logoImage} alt="모아모아 로고" shape="circular" width="38px" height="38px" />
      </S.ImageContainer>
      <S.BorderText>MOAMOA</S.BorderText>
    </Flex>
  );
};

export default Logo;
