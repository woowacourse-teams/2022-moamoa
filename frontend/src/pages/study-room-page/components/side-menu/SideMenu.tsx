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
          <TabButton key={id} onClick={handleTabButtonClick(id)} isSelected={activeTabId === id}>
            {name}
          </TabButton>
        ))}
      </S.Sidebar>
      <S.Bottombar>
        {tabs.map(({ id, name }) => (
          <TabButton key={id} onClick={handleTabButtonClick(id)} isSelected={activeTabId === id}>
            {name}
          </TabButton>
        ))}
      </S.Bottombar>
    </>
  );
};

export default SideMenu;
