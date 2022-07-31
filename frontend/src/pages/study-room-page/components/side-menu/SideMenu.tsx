import { css } from '@emotion/react';

import type { TabId, Tabs } from '@study-room-page/StudyRoomPage';
import * as S from '@study-room-page/components/side-menu/SideMenu.style';
import TabButton from '@study-room-page/components/tab-button/TabButton';

export interface SideMenuProps {
  className?: string;
  activeTabId: TabId;
  tabs: Tabs;
  handleTabButtonClick: React.MouseEventHandler<HTMLButtonElement>;
}

const mb20 = css`
  margin-bottom: 12px;
`;

const SideMenu: React.FC<SideMenuProps> = ({ className, activeTabId, tabs, handleTabButtonClick }) => {
  return (
    <S.Nav className={className}>
      {tabs.map(({ id, name }) => (
        <TabButton key={id} id={id} onClick={handleTabButtonClick} isSelected={activeTabId === id} css={mb20}>
          {name}
        </TabButton>
      ))}
    </S.Nav>
  );
};

export default SideMenu;
