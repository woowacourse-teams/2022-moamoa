import { css } from '@emotion/react';

import type { TabId, Tabs } from '@study-room-page/StudyRoomPage';
import * as S from '@study-room-page/components/side-menu/SideMenu.style';
import TabButton from '@study-room-page/components/tab-button/TabButton';

export type SideMenuProps = {
  className?: string;
  activeTabId: TabId;
  tabs: Tabs;
  onTabButtonClick: (id: string) => React.MouseEventHandler<HTMLButtonElement>;
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
    <S.Sidebar className={className}>
      {tabs.map(({ id, name }) => (
        <TabButton key={id} onClick={handleTabButtonClick(id)} isSelected={activeTabId === id} css={mb12}>
          {name}
        </TabButton>
      ))}
    </S.Sidebar>
  );
};

export default SideMenu;
