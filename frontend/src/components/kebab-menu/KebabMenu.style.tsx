import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const KebabMenuButton = styled.button`
  border: none;
  background-color: transparent;
`;

export const KebabMenu = styled.ul`
  ${({ theme }) => css`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    row-gap: 2px;

    width: 24px;

    li {
      width: 4px;
      height: 4px;
      border-radius: ${theme.radius.circle};
      background-color: ${theme.colors.secondary.dark};
    }
  `}
`;
