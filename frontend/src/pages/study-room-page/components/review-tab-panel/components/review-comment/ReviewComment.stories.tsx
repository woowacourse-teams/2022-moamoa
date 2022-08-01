import { Story } from '@storybook/react';

import type { ReviewCommentProps } from '@study-room-page/components/review-tab-panel/components/review-comment/ReviewComment';
import ReviewComment from '@study-room-page/components/review-tab-panel/components/review-comment/ReviewComment';

export default {
  title: 'Components/ReviewComment',
  component: ReviewComment,
  argTypes: {},
};

const Template: Story<ReviewCommentProps> = props => {
  return <ReviewComment {...props} />;
};

export const Default = Template.bind({});
Default.args = {
  id: 123,
  studyId: 1,
  author: {
    id: 1,
    username: 'nan-noo',
    imageUrl: 'https://avatars.githubusercontent.com/u/54002105?v=4',
    profileUrl: 'https://github.com/nan-noo',
  },
  date: '2022-07-31',
  content:
    '후기후기 라라라랄라ㅏ abcedfsf rksk가나다라 마바사ㅏ 아자ㅏ 아런아렁날 ㅓㅇㄴㄹㄴ아렁나러ㅏㄴ어라먼아러낭ㄹㅇㄴㄹㄴㅇ 낭런아럼;닌아럼;니아런;이ㅏ런일아아아앙 나나나나나나 ㅏㅇㄹㅇㄹ알ㅇㄹㅇㄹㅇ  ㄴㄹ날가나다라가나다라 가낟 ㄴ안ㅇ란ㅇㄹ ㅇ ㅁㄴㅇㄹㅁㄴㅇㄹㅁㄴㅇㄹ ㅇㄴㄹㅁㅇㄴㄴㅇㄹㅇㄴㄹ ㄹㅇㄴㄹㅁㄴㅇㄹㄴㅇㄹㄴㅇㄹ ㄹㅇㄴㅁㄴㅇㄹㅁㄴㅇㄹㄴㅇㄹㄴ ㅇㄹㅇ',
};
