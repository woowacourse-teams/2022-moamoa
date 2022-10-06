import type { Story } from '@storybook/react';
import { useState } from 'react';

import { PATH } from '@constants';

import SideMenu from '@study-room-page/components/side-menu/SideMenu';
import type { SideMenuProps } from '@study-room-page/components/side-menu/SideMenu';
import { type TabId, type Tabs } from '@study-room-page/hooks/useStudyRoomPage';

export default {
  title: 'Pages/StudyRoomPage/SideMenu',
  component: SideMenu,
};

const Template: Story<SideMenuProps> = () => {
  const tabs: Tabs = [
    { id: PATH.NOTICE, name: '공지사항' },
    { id: PATH.COMMUNITY, name: '커뮤니티' },
    { id: PATH.LINK, name: '링크 모음' },
    { id: PATH.REVIEW, name: '후기' },
  ];

  const [activeTabId, setActiveTabId] = useState<TabId>(tabs[0].id);

  const handleTabButtonClick = (id: TabId) => () => {
    setActiveTabId(id);
  };
  return <SideMenu activeTabId={activeTabId} tabs={tabs} onTabButtonClick={handleTabButtonClick} />;
};

export const Default = Template.bind({});
Default.parameters = { controls: { exclude: ['className', 'activeTabId', 'tabs', 'onTabButtonClick'] } };
