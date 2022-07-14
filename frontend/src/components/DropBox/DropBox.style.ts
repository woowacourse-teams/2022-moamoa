import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const DropBoxContainer = styled.div`
  ${({ theme }) => css`
    position: absolute;
    width: fit-content;
    padding: 8px 16px;

    background-color: ${theme.colors.white};
    border: 1px solid ${theme.colors.secondary.base};
    border-radius: 25px;
  `}

  transform-origin: top;
  animation: slidein 0.1s ease;

  @keyframes slidein {
    0% {
      transform: scale(1, 0);
    }
    100% {
      transform: scale(1, 1);
    }
  }
`;

export const FilterSearchBar = styled.div`
  ${({ theme }) => css`
    display: flex;
    align-items: center;

    position: relative;
    margin-bottom: 4px;

    svg {
      position: absolute;
      left: 0;

      stroke: ${theme.colors.secondary.dark};
    }
  `}
`;

export const FilterSearchInput = styled.input`
  ${({ theme }) => css`
    flex-grow: 1;

    width: 100%;
    padding: 8px 20px 4px;

    border: none;
    border-bottom: 1px solid ${theme.colors.secondary.dark};
    outline: none;

    &:focus {
      border-bottom: 1px solid ${theme.colors.primary.light};
    }
  `}
`;

export const FilterList = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 4px;

  width: 100%;
  min-width: 150px;
  max-height: 180px;
  overflow-y: hidden;

  &:hover {
    overflow-y: auto;
  }
`;
