import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Button = styled.button`
  ${({ theme }) => css`
    display: flex;
    justify-content: center;
    align-items: center;

    width: 25px;
    height: 25px;

    text-align: center;
    background-color: ${theme.colors.secondary.light};
    border: 1px solid ${theme.colors.secondary.base};
    border-radius: 50%;

    transition: transform 0.2s ease;

    & > svg {
      fill: ${theme.colors.primary.base};
    }

    &:hover {
      transform: scale(1.2);
    }
  `}
`;
