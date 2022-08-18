import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const CreateNewStudyButton = styled.button`
  ${({ theme }) => css`
    position: fixed;
    right: 60px;
    bottom: 50px;

    display: flex;
    align-items: center;
    justify-content: center;

    width: 70px;
    height: 70px;
    padding: 8px;

    border-radius: 50%;
    border: none;
    background-color: ${theme.colors.primary.base};

    &:hover {
      background-color: ${theme.colors.primary.light};
    }

    &:active {
      background-color: ${theme.colors.primary.dark};
    }
  `}
`;
