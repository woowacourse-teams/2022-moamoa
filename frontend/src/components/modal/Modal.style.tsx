import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const ModalContainer = styled.div``;
export const ModalOutside = styled.div`
  ${({ theme }) => css`
    display: flex;
    justify-content: center;
    align-items: center;

    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 10;

    width: 100vw;
    height: 100%;

    background-color: ${`${theme.colors.secondary.base}33`};
  `}
`;

export const ModalContent = styled.div``;
