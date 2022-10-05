import type { Story } from '@storybook/react';

import tw from '@utils/tw';

import Card, { type CardProps } from '@components/card/Card';
import Image from '@components/image/Image';

export default {
  title: 'Components/Card',
  component: Card,
};

const ImageHeadingContent: Story<CardProps> = props => (
  <Card {...props}>
    <div css={tw`mb-16 flex-grow`}>
      <Image
        shape="rectangular"
        alt="어떤 이미지"
        src="https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8c3R1ZHl8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"
      />
    </div>
    <div>
      <Card.Heading>카드 제목입니다.</Card.Heading>
      <div css={tw`mb-16`}>
        <Card.Content>
          이것은 카드 내용입니다. 어떻게 보이나요? 가나다라마바사 아자차카타파하 hihi abcdefagsdfassdfs
        </Card.Content>
      </div>
      <Card.Content align="right">#JavaScript #React #4기 #FE</Card.Content>
    </div>
  </Card>
);

export const WithImage = ImageHeadingContent.bind({});
WithImage.args = {
  custom: {
    width: '250px',
    height: '300px',
  },
};
WithImage.parameters = { controls: { exclude: ['children'] } };

const HeadingContent: Story<CardProps> = props => (
  <Card {...props}>
    <div css={tw`mb-12`}>
      <Card.Heading>카드 제목입니다.</Card.Heading>
    </div>
    <div css={tw`mb-14`}>
      <Card.Content>hihi</Card.Content>
    </div>
    <div css={tw`mb-14`}>
      <Card.Content>#JavaScript #React #4기 #FE</Card.Content>
    </div>
    <div css={tw`mb-14`}>
      <Card.Content>
        <span>2022-08-31</span> ~ <span>2022-08-31</span>
      </Card.Content>
    </div>
  </Card>
);

export const WithoutImage = HeadingContent.bind({});
WithoutImage.args = {
  custom: {
    width: '250px',
    height: '200px',
  },
};
WithoutImage.parameters = { controls: { exclude: ['children'] } };
