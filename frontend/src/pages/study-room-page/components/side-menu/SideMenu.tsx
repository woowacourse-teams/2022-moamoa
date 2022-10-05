import { Link } from 'react-router-dom';

import { Theme, css, useTheme } from '@emotion/react';

import { mqDown, mqUp } from '@styles/responsive';

import TabButton from '@study-room-page/components/tab-button/TabButton';
import type { TabId, Tabs } from '@study-room-page/hooks/useStudyRoomPage';

export type SideMenuProps = {
  activeTabId: TabId;
  tabs: Tabs;
  onTabButtonClick: (id: TabId) => React.MouseEventHandler<HTMLButtonElement>;
};

const SideMenu: React.FC<SideMenuProps> = ({ activeTabId, tabs, onTabButtonClick: handleTabButtonClick }) => {
  const theme = useTheme();
  return (
    <>
      <Sidebar theme={theme} tabs={tabs} activeTabId={activeTabId} onTabButtonClick={handleTabButtonClick} />
      <BottomBar theme={theme} tabs={tabs} activeTabId={activeTabId} onTabButtonClick={handleTabButtonClick} />
    </>
  );
};

type SidebarProps = {
  theme: Theme;
} & SideMenuProps;
const Sidebar: React.FC<SidebarProps> = ({ theme, tabs, activeTabId, onTabButtonClick: handleTabButtonClick }) => {
  const style = css`
    position: sticky;
    top: 100px;
    left: 0;
    z-index: 1;

    width: 100%;
    max-width: 180px;
    padding: 16px;

    background-color: ${theme.colors.secondary.light};
    text-align: center;

    ${mqDown('lg')} {
      display: none;
    }
  `;
  return (
    <div css={style}>
      {tabs.map(({ id, name }) => (
        <Link key={id} to={id}>
          <TabButton onClick={handleTabButtonClick(id)} isSelected={activeTabId === id}>
            {name}
          </TabButton>
        </Link>
      ))}
    </div>
  );
};

type BottomBarProps = {
  theme: Theme;
} & SideMenuProps;
const BottomBar: React.FC<BottomBarProps> = ({ theme, tabs, activeTabId, onTabButtonClick: handleTabButtonClick }) => {
  const style = css`
    display: flex;
    align-items: space-between;
    column-gap: 16px;

    position: fixed;
    left: 0;
    bottom: 0;
    z-index: 1;

    width: 100%;
    padding: 16px;

    background-color: ${theme.colors.white};
    text-align: center;
    border-top: 1px solid ${theme.colors.secondary.base};

    ${mqUp('lg')} {
      display: none;
    }
  `;
  return (
    <div css={style}>
      {tabs.map(({ id, name }) => (
        <Link key={id} to={id}>
          <TabButton onClick={handleTabButtonClick(id)} isSelected={activeTabId === id}>
            {name}
          </TabButton>
        </Link>
      ))}
    </div>
  );
};

export default SideMenu;
