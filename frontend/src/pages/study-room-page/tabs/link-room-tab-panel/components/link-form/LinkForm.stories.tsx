import { type Story } from '@storybook/react';

import { noop } from '@utils';

import LinkForm, { type LinkFormProps } from '@link-tab/components/link-form/LinkForm';

export default {
  title: 'Pages/StudyRoomPage/LinkForm',
  component: LinkForm,
};

const Template: Story<LinkFormProps> = props => <LinkForm {...props} onPostError={noop} onPostSuccess={noop} />;

export const Default = Template.bind({});
Default.args = {
  author: {
    id: 20,
    username: 'tco0427',
    imageUrl:
      'https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80',
    profileUrl: 'github.com',
  },
};
Default.parameters = { controls: { exclude: ['onPostSuccess', 'onPostError'] } };
