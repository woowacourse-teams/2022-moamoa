import { MakeOptional } from '@custom-types/index';

import * as S from './Avatar.style';

export type AvatarProps = {
  profileImg: string;
  profileAlt: string;
  size: 'sm' | 'md' | 'lg';
};

type OptionalAvatarProps = MakeOptional<AvatarProps, 'size'>;

const Avatar: React.FC<OptionalAvatarProps> = ({ size = 'sm', profileImg, profileAlt }) => {
  return (
    <S.Avatar size={size}>
      <S.Image src={profileImg} alt={profileAlt} />
    </S.Avatar>
  );
};

export default Avatar;
