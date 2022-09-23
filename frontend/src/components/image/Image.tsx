import { css } from '@emotion/react';
import styled from '@emotion/styled';

import notFoundImage from '@assets/images/no-image-found.png';

import type { CssLength } from '@custom-types';

export type ImageProps = {
  shape: 'circular' | 'rectangular';
  src?: string | null;
  alt: string;
  width?: CssLength;
  height?: CssLength;
  objectFit?: 'cover' | 'fill' | 'contain' | 'none' | 'scale-down';
  ratio?: string;
};

const Image: React.FC<ImageProps> = ({
  shape = 'rectangular',
  src,
  alt,
  width = '100%',
  height = '100%',
  objectFit = 'cover',
  ratio = '16 / 10',
}) => {
  const handleImageError = ({ currentTarget }: React.SyntheticEvent<HTMLImageElement>) => {
    currentTarget.src = notFoundImage;
  };

  return (
    <Self
      src={src ?? notFoundImage}
      alt={alt}
      onError={handleImageError}
      shape={shape}
      width={width}
      height={height}
      objectFit={objectFit}
      ratio={ratio}
    />
  );
};

type StyledImageProps = Required<Pick<ImageProps, 'shape' | 'width' | 'height' | 'objectFit'>>;

export const Self = styled.img<StyledImageProps>`
  ${({ theme, shape, width, height, objectFit }) => css`
    width: ${width};
    height: ${height};

    object-fit: ${objectFit};
    object-position: center;

    border-radius: ${theme.radius.sm};

    ${shape === 'circular' &&
    css`
      border-radius: 50%;
    `}
  `}
`;

export default Image;
