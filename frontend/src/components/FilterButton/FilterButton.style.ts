import { css } from '@emotion/react';
import styled from '@emotion/styled';

interface CheckBoxLabelProps {
  isChecked: boolean;
}

export const CheckBoxLabel = styled.label<CheckBoxLabelProps>`
  ${({ theme, isChecked }) => css`
    position: relative;
    width: 80px;
    height: 80px;

    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 8px;

    &:hover {
      border-bottom: 2px solid ${isChecked ? theme.colors.primary.dark : theme.colors.secondary.base};
    }

    border-bottom: 2px solid ${isChecked ? theme.colors.primary.dark : 'none'};
    background-color: transparent;
    cursor: pointer;

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

export const ShortTitle = styled.span`
  font-size: 20px;
  font-weight: 700;
`;

export const Description = styled.span`
  font-size: 12px;
`;
