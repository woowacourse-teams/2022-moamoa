import { Story } from '@storybook/react';

import Wrapper from '@components/Wrapper';

import MarkdownRender from './MarkdownRender';

export default {
  title: 'Components/Markdown',
  component: MarkdownRender,
};

const defaultMarkdown = `
  # MOAMOA

  ## Description

  **What?**

  - 스터디 모집 : 스터디를 개설하고 스터디원을 모집할 수 있습니다.
  - 스터디 조회 : 개설된 모든 스터디 목록을 조회할 수 있습니다.
  - 스터디 관리 : 스터디 일정을 관리하고, 학습한 스터디 자료나 공지사항을 공유할 수 있습니다.
  - 스터디 후기 : 참여한 스터디의 후기를 작성하고 공유할 수 있습니다.

  **Why?**

  새로운 스터디를 개설할 때 이전에 기획된 스터디를 참고할 수 있습니다. 기존 스터디의 일정, 내용을 확인하고 스터디원들의 후기를 참고하여 개설할 스터디의 학습 방향을 정할 수 있습니다.

  스터디원 모집, 학습 내용 및 공지사항 공유를 편리하게 할 수 있으며, 기존 Github이 아닌 새로운 뷰를 제공해 학습 내용을 좀 더 편리하게 확인할 수 있습니다. 스터디 후기를 통해 기획된 스터디의 장단점을 파악할 수 있고, 추후 동일한 주제의 스터디를 개설할 때 유용한 자료로 사용될 수 있습니다.

  ---

  ## Crews

  - [디우(김동규)](https://github.com/tco0427)
  - [태태(김태윤)](https://github.com/nan-noo)
  - [그린론(유재서)](https://github.com/jaejae-yoo)
  - [베루스(정진혁)](https://github.com/wilgur513)
  - [짱구(신승철)](https://github.com/sc0116)
  - [병민(윤병인)](https://github.com/airman5573)
`;

const Template: Story = () => (
  <Wrapper>
    <MarkdownRender markdownContent={defaultMarkdown} />
  </Wrapper>
);

export const Default = Template.bind({});
Default.args = {};
