import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { CssLength } from '@custom-types';

type StyledAvatarProps = {
  size: CssLength;
};

export const Avatar = styled.div<StyledAvatarProps>`
  ${({ size }) => css`
    width: ${size};
    min-width: ${size};
    height: ${size};
  `}
`;
