import { css } from '@emotion/react';
import styled from '@emotion/styled';

interface CheckBoxLabelProps {
  isChecked: boolean;
}

export const FilterButtonContainer = styled.div`
  display: flex;
  align-items: center;

  height: 70px;
`;

export const CheckBoxLabel = styled.label<CheckBoxLabelProps>`
  ${({ theme, isChecked }) => css`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: flex-end;
    gap: 8px;

    position: relative;
    width: 80px;
    padding-bottom: 8px;

    border-bottom: 2px solid ${isChecked ? theme.colors.primary.dark : 'none'};
    background-color: transparent;
    cursor: pointer;

    &:hover {
      border-bottom: 2px solid ${isChecked ? theme.colors.primary.dark : theme.colors.secondary.base};
    }

    & > span {
      color: ${isChecked ? theme.colors.primary.dark : theme.colors.primary.base};
    }
  `}
`;

export const CheckboxInput = styled.input`
  position: absolute;
  width: 0;
  height: 0;

  opacity: 0;
`;

export const ShortName = styled.span`
  font-size: 20px;
  font-weight: 700;
`;

export const FullName = styled.span`
  font-size: 12px;
`;
