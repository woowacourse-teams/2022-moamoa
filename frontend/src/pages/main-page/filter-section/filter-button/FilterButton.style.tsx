import { css } from '@emotion/react';
import styled from '@emotion/styled';

interface CheckBoxButtonProps {
  isChecked: boolean;
}

export const FilterButtonContainer = styled.div`
  display: flex;
  align-items: center;

  height: 70px;
`;

export const CheckBoxButton = styled.button<CheckBoxButtonProps>`
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
    border-bottom: 2px solid ${isChecked ? theme.colors.primary.dark : 'none'};
    background-color: transparent;

    &:hover {
      border-bottom: 2px solid ${isChecked ? theme.colors.primary.dark : theme.colors.secondary.base};
    }

    & > p {
      color: ${isChecked ? theme.colors.primary.dark : theme.colors.primary.base};
    }
  `}
`;

export const ShortName = styled.p`
  font-size: 20px;
  font-weight: 700;
`;

export const FullName = styled.p`
  font-size: 12px;
`;
