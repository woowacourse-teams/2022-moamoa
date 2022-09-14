import type { CssLength, MakeOptional } from '@custom-types';

import * as S from '@components/avatar/Avatar.style';
import Image from '@components/image/Image';

export type AvatarProps = {
  src: string;
  name: string;
  size: keyof typeof AVATAR_SIZE;
};

export const AVATAR_SIZE: Record<'sm' | 'md' | 'lg' | 'xl', CssLength> = {
  sm: '30px',
  md: '36px',
  lg: '42px',
  xl: '65px',
};

type OptionalAvatarProps = MakeOptional<AvatarProps, 'size'>;

const Avatar: React.FC<OptionalAvatarProps> = ({ size = 'md', src, name }) => {
  return (
    <S.Avatar size={AVATAR_SIZE[size]}>
      <Image shape="circular" src={src} alt={name} width={AVATAR_SIZE[size]} height={AVATAR_SIZE[size]} />
    </S.Avatar>
  );
};

export default Avatar;
