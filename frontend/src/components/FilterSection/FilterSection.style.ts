import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const FilterSectionContainer = styled.section`
  ${({ theme }) => css`
    display: flex;
    align-items: center;
    gap: 32px;

    margin-bottom: 16px;
    padding: 16px auto 4px;
    overflow-x: auto;

    border-bottom: 1px solid ${theme.colors.secondary.dark};

    &::-webkit-scrollbar {
      display: none;
    }
  `}
`;

export const FilterSectionHeader = styled.h2`
  display: none;
`;

export const FilterButtons = styled.ul`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 24px;
`;

export const VerticalLine = styled.div`
  ${({ theme }) => css`
    height: 40px;
    border-right: 1px solid ${theme.colors.secondary.dark};
  `}
`;
