import { type Story } from '@storybook/react';

import CrownIcon from '@shared/icons/crown-icon/CrownIcon';

export default {
  title: 'Materials/Icons/CrownIcon',
  component: CrownIcon,
};

const Template: Story = () => <CrownIcon />;

export const Default = Template.bind({});
Default.args = {};
