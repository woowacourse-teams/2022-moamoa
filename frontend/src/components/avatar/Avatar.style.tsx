import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { CssLength } from '@custom-types';

type StyleAvatarProps = {
  size: CssLength;
};

export const Avatar = styled.div<StyleAvatarProps>`
  ${({ size }) => css`
    width: ${size};
    min-width: ${size};
    height: ${size};
  `}
`;
