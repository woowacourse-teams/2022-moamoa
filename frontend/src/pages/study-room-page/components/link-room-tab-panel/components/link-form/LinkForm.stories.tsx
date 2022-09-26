import type { Story } from '@storybook/react';

import LinkForm from '@study-room-page/components/link-room-tab-panel/components/link-form/LinkForm';
import type { LinkFormProps } from '@study-room-page/components/link-room-tab-panel/components/link-form/LinkForm';

export default {
  title: 'Components/LinkForm',
  component: LinkForm,
};

const Template: Story<LinkFormProps> = props => <LinkForm {...props} />;

export const Default = Template.bind({});
Default.args = {};
