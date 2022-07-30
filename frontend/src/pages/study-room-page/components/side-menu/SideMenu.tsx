import { useState } from 'react';

import { css } from '@emotion/react';

import * as S from '@study-room-page/components/side-menu/SideMenu.style';
import TabButton from '@study-room-page/components/tab-button/TabButton';

const tabIdsObject = {
  notice: 'notice',
  material: 'material',
  review: 'review',
};

type TabIds = typeof tabIdsObject[keyof typeof tabIdsObject];

const mb20 = css`
  margin-bottom: 12px;
`;

const SideMenu: React.FC = () => {
  const [activeTab, setActiveTab] = useState<TabIds>(tabIdsObject.notice);

  const handleTabButtonClick = ({ currentTarget: { id } }: React.MouseEvent<HTMLButtonElement>) => {
    setActiveTab(id);
  };

  return (
    <S.Nav>
      <TabButton
        id={tabIdsObject.notice}
        onClick={handleTabButtonClick}
        isSelected={activeTab === tabIdsObject.notice}
        css={mb20}
      >
        공지사항
      </TabButton>
      <TabButton
        id={tabIdsObject.material}
        onClick={handleTabButtonClick}
        isSelected={activeTab === tabIdsObject.material}
        css={mb20}
      >
        자료실
      </TabButton>
      <TabButton id={tabIdsObject.review} onClick={handleTabButtonClick} isSelected={activeTab === tabIdsObject.review}>
        후기
      </TabButton>
    </S.Nav>
  );
};

export default SideMenu;
