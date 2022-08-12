import type { MakeOptional } from '@custom-types';

import * as S from '@components/avatar/Avatar.style';
import CenterImage from '@components/center-image/CenterImage';

export type AvatarProps = {
  profileImg: string;
  profileAlt: string;
  size: 'xs' | 'sm' | 'md' | 'lg';
};

type OptionalAvatarProps = MakeOptional<AvatarProps, 'size'>;

const Avatar: React.FC<OptionalAvatarProps> = ({ size = 'sm', profileImg, profileAlt }) => {
  return (
    <S.Avatar size={size}>
      <CenterImage src={profileImg} alt={profileAlt} />
    </S.Avatar>
  );
};

export default Avatar;
