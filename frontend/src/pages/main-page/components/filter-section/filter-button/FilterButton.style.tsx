import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

import type { FilterButtonProps } from '@main-page/components/filter-section/filter-button/FilterButton';

const applyCheckedStyle = (theme: Theme) => css`
  color: ${theme.colors.primary.base};
  border-bottom: 2px solid ${theme.colors.primary.dark};

  &:hover {
    border-bottom: 2px solid ${theme.colors.primary.dark};
  }

  & > p {
    color: ${theme.colors.primary.dark};
  }
`;

export const FilterButtonContainer = styled.div`
  display: flex;
  align-items: center;

  height: 70px;
`;

export const CheckBoxButton = styled.button<Pick<FilterButtonProps, 'isChecked'>>`
  ${({ theme, isChecked }) => css`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: flex-end;
    row-gap: 8px;

    position: relative;
    width: 80px;
    padding-bottom: 8px;

    border: none;
    border-bottom: 2px solid transparent;
    background-color: transparent;

    &:hover {
      border-bottom: 2px solid ${theme.colors.secondary.base};
    }

    & > p {
      color: ${theme.colors.primary.base};
    }

    ${isChecked && applyCheckedStyle(theme)}
  `}
`;

export const Name = styled.p`
  font-size: 20px;
  font-weight: 700;
`;

export const Description = styled.p`
  font-size: 12px;
`;
