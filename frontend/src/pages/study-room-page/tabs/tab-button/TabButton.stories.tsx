import type { Story } from '@storybook/react';
import { useState } from 'react';

import TabButton from '@study-room-page/tabs/tab-button/TabButton';
import type { TabButtonProps } from '@study-room-page/tabs/tab-button/TabButton';

export default {
  title: 'Components/TabButton',
  component: TabButton,
  argTypes: {
    children: { controls: 'text' },
    isSelected: { controls: 'boolean' },
  },
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
Default.parameters = { controls: { exclude: ['isSelected', 'onClick'] } };

export const Clicked = Template.bind({});
Clicked.args = {
  children: '공지사항',
  isSelected: true,
};
Clicked.parameters = { controls: { exclude: ['isSelected', 'onClick'] } };

export const Unclicked = Template.bind({});
Unclicked.args = {
  children: '공지사항',
  isSelected: false,
};
Unclicked.parameters = { controls: { exclude: ['isSelected', 'onClick'] } };
