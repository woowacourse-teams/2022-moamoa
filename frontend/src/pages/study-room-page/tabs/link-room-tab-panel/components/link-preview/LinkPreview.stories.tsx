import type { Story } from '@storybook/react';

import LinkPreview from '@link-tab/components/link-preview/LinkPreview';
import type { LinkPreviewProps } from '@link-tab/components/link-preview/LinkPreview';

export default {
  title: 'Pages/StudyRoomPage/LinkPreview',
  component: LinkPreview,
};

const Template: Story<LinkPreviewProps> = props => (
  <div
    style={{
      width: '300px',
      height: '254px',
    }}
  >
    <LinkPreview {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = {
  previewResult: {
    title: '합성 컴포넌트 어쩌구 저쩌구 쏼라쏼라',
    description: '카카오 엔터테인먼트 FE 기술 블로그',
    imageUrl:
      'https://images.unsplash.com/photo-1572059002053-8cc5ad2f4a38?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fGdvb2dsZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60',
    domainName: 'naver.com',
  },
  linkUrl: 'https://naver.com',
};
