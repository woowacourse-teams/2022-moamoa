import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { UnstyledButton } from '@components/button/unstyled-button/UnstyledButton.style';
import ImportedDropDownBox from '@components/drop-down-box/DropDownBox';

export const Container = styled.div`
  position: relative;
  width: 100%;
`;

export const SelectControl = styled.div<{ invalid: boolean }>`
  ${({ theme, invalid }) => css`
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    align-items: center;

    position: relative;
    padding-right: 8px;
    min-height: 38px;

    border: 1px solid ${theme.colors.secondary.base};
    background-color: ${theme.colors.secondary.light};
    border-radius: ${theme.radius.sm};

    ${invalid &&
    css`
      border: 1px solid ${theme.colors.red};

      &:focus {
        border: 1px solid ${theme.colors.red};
      }
    `}
  `}
`;

export const SelectedOptionList = styled.ul`
  display: flex;
  flex: 1 1 0;
  flex-wrap: wrap;
  align-items: center;

  position: relative;

  padding: 2px 8px;
  overflow: hidden;
`;

export const SelectedOption = styled.li`
  ${({ theme }) => css`
    display: flex;

    margin: 2px;

    background-color: ${theme.colors.secondary.base};
    border-radius: 2px;
    font-size: 14px;
  `}
`;

export const SelectedOptionValue = styled.div`
  padding: 3px 3px 3px 6px;

  font-size: 12px;
  border-radius: 2px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

export const UnselectButton = styled(UnstyledButton)`
  display: flex;
  align-items: center;

  font-size: 14px;

  border-radius: 2px;
  padding-left: 4px;
  padding-right: 4px;
`;

export const Indicators = styled.div`
  display: flex;
  align-items: center;
  align-self: stretch;

  flex-shrink: 0;
  row-gap: 10px;
`;

export const DropDown = styled(ImportedDropDownBox)`
  max-height: 180px;
`;

export const UnselectedOption = styled.li`
  ${({ theme }) => css`
    font-size: 20px;

    &:hover {
      background-color: ${theme.colors.white}; // TODO: 색 세분화 필요
    }
  `}
`;

export const SelectButton = styled(UnstyledButton)`
  width: 100%;
  height: 100%;
  padding: 10px;

  text-align: left;
`;

export const AllClearButton = styled(UnstyledButton)``;
