import type { Story } from '@storybook/react';

import LinkRoomTabPanel from '@link-tab/LinkRoomTabPanel';

export default {
  title: 'Pages/StudyRoomPage/LinkRoomTabPanel',
  component: LinkRoomTabPanel,
};

const Template: Story = props => <LinkRoomTabPanel {...props} />;

export const Default = Template.bind({});
Default.args = {};
