import logoImage from '@assets/images/logo.png';

import * as S from './style';

type Props = {
  className: string;
};

const Logo: React.FC<Props> = ({ className }) => {
  return (
    <S.Row className={className}>
      <S.ImageContainer>
        <img src={logoImage} alt="모아모아 로고 이미지" />
      </S.ImageContainer>
      <S.BorderText>MOAMOA</S.BorderText>
    </S.Row>
  );
};

export default Logo;
