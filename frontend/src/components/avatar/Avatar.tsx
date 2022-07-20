import { MakeOptional } from '@custom-types/index';

import * as S from '@components/avatar/Avatar.style';

export type AvatarProps = {
  className?: string;
  profileImg: string;
  profileAlt: string;
  size: 'sm' | 'md' | 'lg';
};

type OptionalAvatarProps = MakeOptional<AvatarProps, 'size'>;

const Avatar: React.FC<OptionalAvatarProps> = ({ className, size = 'sm', profileImg, profileAlt }) => {
  return (
    <S.Avatar className={className} size={size}>
      <S.AvatarImage src={profileImg} alt={profileAlt} />
    </S.Avatar>
  );
};

export default Avatar;
