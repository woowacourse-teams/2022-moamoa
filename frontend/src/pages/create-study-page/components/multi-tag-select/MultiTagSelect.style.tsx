import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { UnstyledButton } from '@components/unstyled-button/UnstyledButton.style';

export { Center } from '@components/center/Center.style';

export const Container = styled.div`
  width: 100%;
  position: relative;
`;

export const SelectControl = styled.div`
  -webkit-box-align: center;
  align-items: center;
  background-color: rgb(255, 255, 255);
  border-color: rgb(204, 204, 204);
  border-radius: 4px;
  border-style: solid;
  border-width: 1px;
  display: flex;
  flex-wrap: wrap;
  -webkit-box-pack: justify;
  justify-content: space-between;
  min-height: 38px;
  position: relative;
  box-sizing: border-box;
  padding-right: 8px;
`;

export const SelectedOptionList = styled.ul`
  -webkit-box-align: center;
  align-items: center;
  display: flex;
  flex: 1 1 0%;
  flex-wrap: wrap;
  padding: 2px 8px;
  position: relative;
  overflow: hidden;
  box-sizing: border-box;
`;

export const SelectedOptionListItem = styled.li`
  background-color: rgb(230, 230, 230);
  border-radius: 2px;
  display: flex;
  margin: 2px;
  min-width: 0px;
  box-sizing: border-box;
  font-size: 14px;
`;

export const SelectedOption = styled.div`
  border-radius: 2px;
  color: rgb(51, 51, 51);
  font-size: 12px;
  overflow: hidden;
  padding: 3px 3px 3px 6px;
  text-overflow: ellipsis;
  white-space: nowrap;
  box-sizing: border-box;
`;

export const RemoveSelectedOptionButton = styled(UnstyledButton)`
  ${({ theme }) => css`
    -webkit-box-align: center;
    align-items: center;
    border-radius: 2px;
    display: flex;
    padding-left: 4px;
    padding-right: 4px;
    box-sizing: border-box;

    &:hover {
      background-color: ${theme.colors.red};
    }
  `}
`;

export const Indicators = styled.div`
  -webkit-box-align: center;
  align-items: center;
  align-self: stretch;
  display: flex;
  flex-shrink: 0;
  box-sizing: border-box;
  row-gap: 10px;
`;

export const SelectMenuContainer = styled.div`
  position: absolute;
  top: calc(100% + 10px);
  left: 0;
  right: 0;
  max-height: 500px;
  overflow-y: scroll;
  background-color: white;
  box-shadow: 0px 0px 3px 1px;

  border-radius: 4px;
`;

export const SelectMenuItem = styled.li`
  ${({ theme }) => css`
    font-size: 20px;
    &:hover {
      background-color: ${theme.colors.primary.light};
      color: white;
    }
  `}
`;

export const SelectMenuItemButton = styled(UnstyledButton)`
  width: 100%;
  height: 100%;
  padding: 10px;
  text-align: left;
`;
