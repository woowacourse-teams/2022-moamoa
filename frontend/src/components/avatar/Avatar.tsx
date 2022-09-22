import * as CSS from 'csstype';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { CssLength, MakeOptional } from '@custom-types';

import Image, { type ImageProps } from '@components/image/Image';

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
    <Self size={AVATAR_SIZE[size]}>
      <AvatarImage src={src} alt={name} size={size} />
    </Self>
  );
};

type StyledAvatarProps = {
  size: CssLength;
};

const Self = styled.div<StyledAvatarProps>`
  ${({ size }) => css`
    width: ${size};
    min-width: ${size};
    height: ${size};
  `}
`;

type AvatarImageProps = Omit<ImageProps, keyof CSS.Properties | 'shape'> & {
  src: AvatarProps['src'];
  size: AvatarProps['size'];
  alt: AvatarProps['name'];
};

const AvatarImage: React.FC<AvatarImageProps> = ({ src, alt, size }) => (
  <Image shape="circular" src={src} alt={alt} width={AVATAR_SIZE[size]} height={AVATAR_SIZE[size]} />
);

export default Avatar;
