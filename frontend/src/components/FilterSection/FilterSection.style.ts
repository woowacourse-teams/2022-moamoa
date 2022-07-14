import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const FilterSectionContainer = styled.section`
  ${({ theme }) => css`
    display: flex;
    align-items: center;
    justify-content: flex-start;
    gap: 16px;

    height: 50px;
    padding: 16px 0;
    margin-bottom: 16px;

    border-bottom: 1px solid ${theme.colors.secondary.dark};
  `}
`;

export const FilterChipList = styled.ul`
  display: flex;
  gap: 12px;
`;
