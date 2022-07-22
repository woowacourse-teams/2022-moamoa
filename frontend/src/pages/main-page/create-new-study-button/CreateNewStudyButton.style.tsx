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

    border-radius: 50%;
    border: 1px solid black;
    background-color: ${theme.colors.primary.base};
    box-shadow: 4px 4px 0px 0px #cfd8dc;
  `}
`;
