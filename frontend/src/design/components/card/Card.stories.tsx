import type { Story } from '@storybook/react';

import tw from '@utils/tw';

import Card, { type CardProps } from '@design/components/card/Card';
import CardContent from '@design/components/card/card-content/CardContent';
import CardHeading from '@design/components/card/card-heading/CardHeading';
import Image from '@design/components/image/Image';

export default {
  title: 'Design/Components/Card',
  component: Card,
};

const ImageHeadingContent: Story<CardProps> = props => (
  <Card {...props}>
    <div css={tw`mb-16 flex-grow`}>
      <Image
        shape="rectangular"
        alt="어떤 이미지"
        src="https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8c3R1ZHl8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"
        width="100%"
        height="100%"
      />
    </div>
    <div>
      <CardHeading>카드 제목입니다.</CardHeading>
      <div css={tw`mb-16`}>
        <CardContent>
          이것은 카드 내용입니다. 어떻게 보이나요? 가나다라마바사 아자차카타파하 hihi abcdefagsdfassdfs
        </CardContent>
      </div>
      <CardContent align="right">#JavaScript #React #4기 #FE</CardContent>
    </div>
  </Card>
);

export const WithImage = ImageHeadingContent.bind({});
WithImage.args = {
  width: '250px',
  height: '300px',
};
WithImage.parameters = { controls: { exclude: ['children'] } };

const HeadingContent: Story<CardProps> = props => (
  <Card {...props}>
    <div css={tw`mb-12`}>
      <CardHeading>카드 제목입니다.</CardHeading>
    </div>
    <div css={tw`mb-14`}>
      <CardContent>hihi</CardContent>
    </div>
    <div css={tw`mb-14`}>
      <CardContent>#JavaScript #React #4기 #FE</CardContent>
    </div>
    <div css={tw`mb-14`}>
      <CardContent>
        <span>2022-08-31</span> ~ <span>2022-08-31</span>
      </CardContent>
    </div>
  </Card>
);

export const WithoutImage = HeadingContent.bind({});
WithoutImage.args = {
  width: '250px',
  height: '200px',
};
WithoutImage.parameters = { controls: { exclude: ['children'] } };
