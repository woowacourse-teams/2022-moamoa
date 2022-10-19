import { type Story } from '@storybook/react';
import { useState } from 'react';

import TabButton, { type TabButtonProps } from '@study-room-page/components/tab-button/TabButton';

export default {
  title: 'Pages/StudyRoomPage/TabButton',
  component: TabButton,
};

const Template: Story<TabButtonProps> = props => {
  const [isSelected, setIsSelected] = useState<boolean>(false);

  const handleTabButtonClick = () => {
    setIsSelected(prev => !prev);
  };

  return (
    <div style={{ width: '200px' }}>
      <TabButton {...props} isSelected={isSelected} onClick={handleTabButtonClick} />
    </div>
  );
};

export const Default = Template.bind({});
Default.args = {
  children: '공지사항',
  isSelected: false,
};
Default.parameters = { controls: { exclude: ['onClick'] } };
