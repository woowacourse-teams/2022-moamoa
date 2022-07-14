import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const StyledFilterChip = styled.div`
  ${({ theme }) => css`
    display: flex;
    align-items: center;
    justify-content: space-between;

    position: relative;

    min-width: 92px;
    width: fit-content;
    max-width: 100%;
    padding: 6px 22.8px 6px 12.8px;

    border-radius: 16px;
    border: 2px solid ${theme.colors.primary.base};
    background-color: ${theme.colors.white};
  `}
`;

export const FilterSpan = styled.span`
  ${({ theme }) => css`
    flex-grow: 1;

    color: ${theme.colors.primary.base};
    text-align: center;
    user-select: none;
    text-overflow: ellipsis;
    overflow: hidden;
  `}
`;

export const FilterCloseButton = styled.button`
  ${({ theme }) => css`
    display: flex;
    align-items: center;
    justify-content: center;

    position: absolute;
    right: 6px;

    border: none;
    background-color: transparent;

    svg {
      stroke: ${theme.colors.primary.base};
    }

    &:hover,
    &:active {
      svg {
        stroke: ${theme.colors.primary.light};
      }
    }
  `}
`;
