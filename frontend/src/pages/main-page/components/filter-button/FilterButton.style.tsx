import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Name = styled.span`
  ${({ theme }) => css`
    margin-bottom: 4px;

    font-size: ${theme.fontSize.lg};
    font-weight: ${theme.fontWeight.bold};
  `}
`;

export const Description = styled.span`
  ${({ theme }) => css`
    font-size: ${theme.fontSize.sm};
  `}
`;
