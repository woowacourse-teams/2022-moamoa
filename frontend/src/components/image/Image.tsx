import { css } from '@emotion/react';
import styled from '@emotion/styled';

import notFoundImage from '@assets/images/no-image-found.png';

import { CustomCSS, resolveCustomCSS } from '@styles/custom-css';

export type ImageProps = {
  shape: 'circular' | 'rectangular';
  src?: string | null;
  alt: string;
  objectFit?: 'cover' | 'fill' | 'contain' | 'none' | 'scale-down';
  ratio?: string;
  custom?: CustomCSS<'width' | 'height' | 'marginBottom'>;
};

const Image: React.FC<ImageProps> = ({
  shape = 'rectangular',
  src,
  alt,
  objectFit = 'cover',
  ratio = '16 / 10',
  custom,
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
      objectFit={objectFit}
      ratio={ratio}
      css={resolveCustomCSS(custom)}
    />
  );
};

type StyledImageProps = Required<Pick<ImageProps, 'shape' | 'objectFit' | 'ratio'>>;

export const Self = styled.img<StyledImageProps>`
  ${({ theme, shape, objectFit, ratio }) => css`
    width: 100%;
    height: 100%;

    object-fit: ${objectFit};
    object-position: center;

    aspect-ratio: ${ratio};

    border-radius: ${theme.radius.sm};

    ${shape === 'circular' &&
    css`
      border-radius: 50%;
    `}
  `}
`;

export default Image;
