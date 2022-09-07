import { Textarea as OriginalTextArea } from '@notice-tab/components/textarea/Textarea.style';

import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

export const EditContent = styled.div`
  ${({ theme }) => css`
    border-radius: ${theme.radius.xs};
    border: 1px solid ${theme.colors.secondary.dark};
    overflow: hidden;

    margin-bottom: 20px;
  `}
`;

export const TabListContainer = styled.div`
  ${({ theme }) => css`
    padding-left: 10px;
    padding-top: 10px;
    background-color: ${theme.colors.secondary.light};
    border-bottom: 1px solid ${theme.colors.secondary.dark};
  `};
`;

export const TabList = styled.ul`
  display: flex;
`;

export const Tab = styled.li``;

type TabItemButtonProps = {
  isActive: boolean;
};

const activeTabItemButtonStyle = (theme: Theme) => css`
  background-color: ${theme.colors.white};
  color: ${theme.colors.black};
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
  border: 1px solid ${theme.colors.secondary.dark};
  border-bottom: none;
`;

export const TabItemButton = styled.button<TabItemButtonProps>`
  ${({ theme, isActive }) => css`
    border: none;
    line-height: 24px;
    font-weight: ${theme.fontWeight.bold};
    padding: 8px 16px;
    transition: color 0.2s cubic-bezier(0.3, 0, 0.5, 1);
    background-color: inherit;
    color: ${theme.colors.secondary.base};

    margin-bottom: -1px;

    ${isActive && activeTabItemButtonStyle(theme)}
  `}
`;

export const Label = styled.label`
  display: block;

  height: 0;
  width: 0;

  visibility: hidden;
`;

export const Textarea = styled(OriginalTextArea)`
  ${({ theme }) => css`
    display: block;

    width: 100%;
    height: 100%;
    padding: 12px;

    border-radius: ${theme.radius.xs};
    background-color: ${theme.colors.secondary.light};
    border: 1px solid ${theme.colors.secondary.dark};
    font-size: ${theme.fontSize.md};

    &:focus {
      background-color: ${theme.colors.white};
    }
  `}
`;

export const TabPanelsContainer = styled.div`
  ${({ theme }) => css`
    padding: 10px;
    background-color: ${theme.colors.white};
    height: 50vh;
  `}
`;

export const TabPanels = styled.div`
  min-height: 100%;
  height: 100%;
`;

type TabPanelProps = {
  isActive: boolean;
};

export const TabPanel = styled.div<TabPanelProps>`
  ${({ isActive }) => css`
    height: 100%;
    display: ${isActive ? 'block' : 'none'};
  `}
`;

export const TabContent = styled.div`
  height: 100%;
`;
