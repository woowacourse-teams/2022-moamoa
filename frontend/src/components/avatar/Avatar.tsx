import { MakeOptional } from '@custom-types';

import * as S from '@components/avatar/Avatar.style';

export type AvatarProps = {
  profileImg: string;
  profileAlt: string;
  size: 'sm' | 'md' | 'lg';
};

type OptionalAvatarProps = MakeOptional<AvatarProps, 'size'>;

const Avatar: React.FC<OptionalAvatarProps> = ({ size = 'sm', profileImg, profileAlt }) => {
  return (
    <S.Avatar size={size}>
      <S.AvatarImage src={profileImg} alt={profileAlt} />
    </S.Avatar>
  );
};

export default Avatar;
