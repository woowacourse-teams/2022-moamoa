import { css } from '@emotion/react';

import TabButton from '@study-room-page/components/tab-button/TabButton';
import type { TabId, Tabs } from '@study-room-page/hooks/useStudyRoomPage';
import * as S from '@study-room-page/tabs/side-menu/SideMenu.style';

export type SideMenuProps = {
  className?: string;
  activeTabId: TabId;
  tabs: Tabs;
  onTabButtonClick: (id: TabId) => React.MouseEventHandler<HTMLButtonElement>;
};

const mb12 = css`
  margin-bottom: 12px;
`;

const SideMenu: React.FC<SideMenuProps> = ({
  className,
  activeTabId,
  tabs,
  onTabButtonClick: handleTabButtonClick,
}) => {
  return (
    <>
      <S.Sidebar className={className}>
        {tabs.map(({ id, name }) => (
          <TabButton key={id} onClick={handleTabButtonClick(id)} isSelected={activeTabId === id} css={mb12}>
            {name}
          </TabButton>
        ))}
      </S.Sidebar>
      <S.Bottombar>
        {tabs.map(({ id, name }) => (
          <TabButton key={id} onClick={handleTabButtonClick(id)} isSelected={activeTabId === id} css={mb12}>
            {name}
          </TabButton>
        ))}
      </S.Bottombar>
    </>
  );
};

export default SideMenu;
