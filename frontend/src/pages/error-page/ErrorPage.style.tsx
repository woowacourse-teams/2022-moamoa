import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Page = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  row-gap: 4px;

  height: calc(100vh - 300px);
`;

export const HomeButton = styled.button`
  ${({ theme }) => css`
    padding: 8px 16px;

    color: ${theme.colors.white};
    background-color: ${theme.colors.primary.base};
    border: none;
    border-radius: ${theme.radius.md};
  `}
`;
