import * as S from './style';

export interface AvatarProps {
  profileImg: string;
  profileAlt: string;
}

const Avatar: React.FC<AvatarProps> = ({ profileImg, profileAlt }) => {
  return (
    <S.ImageContainer>
      <S.Image src={profileImg} alt={profileAlt} />
    </S.ImageContainer>
  );
};

export default Avatar;
