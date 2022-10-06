import { Link } from 'react-router-dom';

import * as S from '@study-room-page/components/side-menu/SideMenu.style';
import TabButton from '@study-room-page/components/tab-button/TabButton';
import type { TabId, Tabs } from '@study-room-page/hooks/useStudyRoomPage';

export type SideMenuProps = {
  activeTabId: TabId;
  tabs: Tabs;
  onTabButtonClick: (id: TabId) => React.MouseEventHandler<HTMLButtonElement>;
};

const SideMenu: React.FC<SideMenuProps> = ({ activeTabId, tabs, onTabButtonClick: handleTabButtonClick }) => {
  return (
    <>
      <S.Sidebar>
        {tabs.map(({ id, name }) => (
          <Link key={id} to={id}>
            <TabButton onClick={handleTabButtonClick(id)} isSelected={activeTabId === id}>
              {name}
            </TabButton>
          </Link>
        ))}
      </S.Sidebar>
      <S.Bottombar>
        {tabs.map(({ id, name }) => (
          <Link key={id} to={id}>
            <TabButton onClick={handleTabButtonClick(id)} isSelected={activeTabId === id}>
              {name}
            </TabButton>
          </Link>
        ))}
      </S.Bottombar>
    </>
  );
};

export default SideMenu;
