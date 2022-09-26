import type { Story } from '@storybook/react';

import UserDescription from '@study-room-page/tabs/link-room-tab-panel/components/user-description/UserDescription';
import type { UserDescriptionProps } from '@study-room-page/tabs/link-room-tab-panel/components/user-description/UserDescription';

export default {
  title: 'Components/UserDescription',
  component: UserDescription,
};

const Template: Story<UserDescriptionProps> = props => (
  <div
    style={{
      width: '240px',
    }}
  >
    <UserDescription {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  author: {
    id: 1,
    username: 'your-name',
    profileUrl: '/',
    imageUrl:
      'https://images.unsplash.com/photo-1554151228-14d9def656e4?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60',
  },
  description: '안녕하세요 글자는 약 40자 정도로 하면 좋을 것 같네요. 가나다라마바사아',
};
