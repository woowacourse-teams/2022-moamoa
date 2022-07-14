import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const FilterContainer = styled.div`
  display: flex;
  gap: 8px;
`;

export const FilterLabel = styled.h2`
  ${({ theme }) => css`
    display: flex;
    align-items: center;
    gap: 4px;

    color: ${theme.colors.primary.base};
    user-select: none;

    svg {
      fill: ${theme.colors.primary.base};
    }
  `}
`;

export const FilterText = styled.span`
  ${({ theme }) => css`
    font-size: 20px;
    font-weight: 700;
    color: ${theme.colors.primary.base};
  `}
`;

export const FilterButtonContainer = styled.div`
  position: relative;
`;

export const FilterButton = styled.button`
  ${({ theme }) => css`
    display: flex;
    justify-content: center;
    align-items: center;

    padding: 8px;

    border: none;
    border-radius: 50%;
    color: ${theme.colors.white};
    background-color: ${theme.colors.primary.base};
    box-shadow: 2px 2px 2px ${theme.colors.secondary.dark};

    &:hover,
    &:active {
      background-color: ${theme.colors.primary.dark};
    }

    svg {
      fill: ${theme.colors.white};
    }
  `}
`;

export const FilterListItem = styled.li`
  cursor: pointer;
`;
