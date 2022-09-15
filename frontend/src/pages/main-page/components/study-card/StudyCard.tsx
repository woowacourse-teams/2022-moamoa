import { memo } from 'react';

import tw from '@utils/tw';

import type { Study } from '@custom-types';

import * as S from '@pages/main-page/components/study-card/StudyCard.style';

import Card from '@components/card/Card';
import Image from '@components/image/Image';
import StudyChip from '@components/study-chip/StudyChip';

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
          <Image shape="rectangular" alt={thumbnailAlt} src={thumbnailUrl} />
        </div>
        <div>
          <Card.Heading>{title}</Card.Heading>
          <div css={tw`mb-8`}>
            <Card.Content>{excerpt}</Card.Content>
          </div>
          <Card.Content align="right" maxLine={1}>
            {tags.map(tag => (
              <span key={tag.id} css={tw`mr-8`}>
                #{tag.name}
              </span>
            ))}
          </Card.Content>
        </div>
        <div css={tw`absolute top-8 right-8`}>
          <StudyChip isOpen={isOpen} />
        </div>
      </Card>
    </S.StudyCardContainer>
  );
};

export default memo(StudyCard);
