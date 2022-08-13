import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const LinkItemContainer = styled.div`
  position: relative;
  width: 300px;
  height: 250px;
`;

export const PreviewMeatballMenuContainer = styled.div`
  ${({ theme }) => css`
    display: flex;
    justify-content: center;
    align-items: center;

    position: absolute;
    top: 8px;
    right: 8px;
    width: 30px;
    height: 30px;

    background-color: ${theme.colors.white};
    border-radius: 50%;
    z-index: 3;
  `}
`;

export const MeatballMenuButton = styled.button`
  background-color: transparent;
  border: none;
`;

export const DropBoxButtons = styled.ul`
  ${({ theme }) => css`
    display: flex;
    flex-direction: column;
    row-gap: 8px;
    padding: 8px;

    & > li:first-of-type {
      padding-bottom: 8px;
      border-bottom: 1px solid ${theme.colors.secondary.base};
    }
  `}
`;

export const DropBoxButton = styled.button`
  padding: 10px;

  background-color: transparent;
  border: none;
  white-space: nowrap;
`;
