import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type ImageProps } from '@components/image/Image';

type StyledImageProps = Required<Pick<ImageProps, 'shape' | 'width' | 'height' | 'objectFit'>>;

export const Image = styled.img<StyledImageProps>`
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
