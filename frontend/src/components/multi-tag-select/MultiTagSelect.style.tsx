import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { UnstyledButton } from '@components/button/unstyled-button/UnstyledButton.style';
import ImportedDropDownBox from '@components/drop-down-box/DropDownBox';

export const Container = styled.div`
  position: relative;
  width: 100%;
`;

export const SelectControl = styled.div`
  position: relative;

  display: flex;
  flex-wrap: wrap;
  -webkit-box-pack: justify;
  justify-content: space-between;
  align-items: center;

  min-height: 38px;

  -webkit-box-align: center;
  background-color: rgb(255, 255, 255);
  border-color: rgb(204, 204, 204);
  border-radius: 4px;
  border-style: solid;
  border-width: 1px;
  padding-right: 8px;
`;

export const SelectedOptionList = styled.ul`
  position: relative;

  display: flex;
  flex: 1 1 0%;
  flex-wrap: wrap;
  align-items: center;

  -webkit-box-align: center;
  padding: 2px 8px;
  overflow: hidden;
`;

export const SelectedOption = styled.li`
  ${({ theme }) => css`
    display: flex;

    background-color: ${theme.colors.secondary.base};
    border-radius: 2px;
    margin: 2px;
    font-size: 14px;
  `}
`;

export const SelectedOptionValue = styled.div`
  padding: 3px 3px 3px 6px;
  font-size: 12px;

  border-radius: 2px;
  color: rgb(51, 51, 51);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

export const UnselectButton = styled(UnstyledButton)`
  display: flex;
  align-items: center;

  font-size: 14px;

  -webkit-box-align: center;
  border-radius: 2px;
  padding-left: 4px;
  padding-right: 4px;
`;

export const Indicators = styled.div`
  display: flex;
  align-items: center;
  align-self: stretch;
  -webkit-box-align: center;

  flex-shrink: 0;
  row-gap: 10px;
`;

export const DropDown = styled(ImportedDropDownBox)`
  max-height: 180px;
  background-color: white;
  box-shadow: 0px 0px 4px 0px;

  border-radius: 4px;
`;

export const UnselectedOption = styled.li`
  ${({ theme }) => css`
    font-size: 20px;
    &:hover {
      background-color: ${theme.colors.secondary.light}; // TODO: 색 세분화 필요
      color: white;
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
