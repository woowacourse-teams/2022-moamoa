import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const MoreButton = styled.button`
  ${({ theme }) => css`
    background-color: transparent;
    border: none;
    font-size: ${theme.fontSize.md};
  `}
`;
