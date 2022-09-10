import { memo } from 'react';

import tw from '@utils/tw';

import type { Study } from '@custom-types';

import * as S from '@pages/main-page/components/study-card/StudyCard.style';

import Card from '@design/components/card/Card';
import CardContent from '@design/components/card/card-content/CardContent';
import CardHeading from '@design/components/card/card-heading/CardHeading';
import Chip from '@design/components/chip/Chip';
import Image from '@design/components/image/Image';

export type StudyCardProps = {
  thumbnailUrl: Study['thumbnail'];
  thumbnailAlt: string;
  title: Study['title'];
  excerpt: Study['excerpt'];
  tags: Study['tags'];
  isOpen: boolean;
};

const StudyCard: React.FC<StudyCardProps> = ({ thumbnailUrl, thumbnailAlt, title, excerpt, tags, isOpen }) => {
  return (
    <S.StudyCardContainer>
      <Card height="280px">
        <div css={tw`mb-16 flex-grow overflow-hidden`}>
          <Image shape="rectangular" alt={thumbnailAlt} src={thumbnailUrl} width="100%" height="100%" />
        </div>
        <div>
          <CardHeading>{title}</CardHeading>
          <div css={tw`mb-8`}>
            <CardContent>{excerpt}</CardContent>
          </div>
          <CardContent align="right" maxLine={1}>
            {tags.map(tag => (
              <span key={tag.id} css={tw`mr-8`}>
                #{tag.name}
              </span>
            ))}
          </CardContent>
        </div>
        <div css={tw`absolute top-8 right-8`}>
          <Chip variant={isOpen ? 'primary' : 'secondary'}>{isOpen ? '모집중' : '모집완료'}</Chip>
        </div>
      </Card>
    </S.StudyCardContainer>
  );
};

export default memo(StudyCard);
