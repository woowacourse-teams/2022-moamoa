import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const KebabMenu = styled.ul`
  ${({ theme }) => css`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    row-gap: 2px;

    width: 15px;

    li {
      width: 4px;
      height: 4px;
      border-radius: 50%;
      background-color: ${theme.colors.secondary.dark};
    }

    cursor: pointer;
  `}
`;
