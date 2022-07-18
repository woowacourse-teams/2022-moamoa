import logoImage from '@assets/images/logo.png';

import * as S from './Logo.style';

const Logo: React.FC = () => {
  return (
    <S.Row>
      <S.ImageContainer>
        <img src={logoImage} alt="모아모아 로고 이미지" />
      </S.ImageContainer>
      <S.BorderText>MOAMOA</S.BorderText>
    </S.Row>
  );
};

export default Logo;
