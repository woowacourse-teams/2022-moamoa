import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Footer = styled.footer`
  ${({ theme }) => css`
    padding: 24px 0;

    text-align: center;
    color: ${theme.colors.secondary.dark};
    border-top: 1px solid ${theme.colors.secondary.dark};
  `}
`;
