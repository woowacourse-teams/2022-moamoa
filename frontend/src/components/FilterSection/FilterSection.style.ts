import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const FilterSectionContainer = styled.section`
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 8px;

  overflow-x: auto;

  &::-webkit-scrollbar {
    display: none;
  }
`;

export const FilterSectionHeader = styled.h2`
  display: none;
`;

export const FilterButtons = styled.ul`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 4px;
`;

export const VerticalLine = styled.div`
  ${({ theme }) => css`
    height: 40px;
    border-right: 1px solid ${theme.colors.secondary.dark};
  `}
`;
