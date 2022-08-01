import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Sidebar = styled.nav`
  ${({ theme }) => css`
    width: 100%;
    max-width: 180px;
    padding: 16px;

    background-color: ${theme.colors.secondary.light};
    text-align: center;
    z-index: 1;
  `}
`;
