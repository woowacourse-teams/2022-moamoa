import type { Story } from '@storybook/react';

import LinkRoomTabPanel from '@study-room-page/components/link-room-tab-panel/LinkRoomTabPanel';

export default {
  title: 'Components/LinkRoomTabPanel',
  component: LinkRoomTabPanel,
};

const Template: Story = props => <LinkRoomTabPanel {...props} />;

export const Default = Template.bind({});
Default.args = {};
