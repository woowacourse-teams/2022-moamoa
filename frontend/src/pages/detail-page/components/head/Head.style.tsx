import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Excerpt = styled.p`
  ${({ theme }) => css`
    padding: 8px 0 16px;

    font-size: ${theme.fontSize.xl};
  `}
`;
