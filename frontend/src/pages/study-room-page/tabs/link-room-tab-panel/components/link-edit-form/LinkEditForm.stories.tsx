import type { Story } from '@storybook/react';

import { noop } from '@utils';

import LinkEditForm, { type LinkEditFormProps } from '@link-tab/components/link-edit-form/LinkEditForm';

export default {
  title: 'Pages/StudyRoomPage/LinkEditForm',
  component: LinkEditForm,
};

const Template: Story<LinkEditFormProps> = props => <LinkEditForm {...props} onPutError={noop} onPutSuccess={noop} />;

export const Default = Template.bind({});
Default.args = {
  linkId: 1,
  author: {
    id: 20,
    username: 'tco0427',
    imageUrl:
      'https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80',
    profileUrl: 'github.com',
  },
  originalContent: {
    linkUrl: 'https://naver.com',
    description: '네이버 홈',
  },
};
Default.parameters = { controls: { exclude: ['onPostSuccess', 'onPostError'] } };
