import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type ImageProps } from '@design/components/image/Image';

type StyleImageProps = Pick<ImageProps, 'shape' | 'width' | 'height'>;

export const Image = styled.img<StyleImageProps>`
  ${({ shape, width, height }) => css`
    width: ${width};
    height: ${height};

    object-fit: cover;
    object-position: center;

    ${shape === 'circular' && 'border-radius: 50%;'}
  `}
`;
