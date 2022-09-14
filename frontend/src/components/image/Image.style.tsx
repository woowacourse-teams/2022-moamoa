import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type ImageProps } from '@components/image/Image';

type StyleImageProps = Pick<ImageProps, 'shape' | 'width' | 'height'>;

export const Image = styled.img<StyleImageProps>`
  ${({ theme, shape, width, height }) => css`
    width: ${width};
    height: ${height};

    object-fit: cover;
    object-position: center;

    border-radius: ${theme.radius.sm};

    ${shape === 'circular' &&
    css`
      border-radius: 50%;
    `}
  `}
`;
