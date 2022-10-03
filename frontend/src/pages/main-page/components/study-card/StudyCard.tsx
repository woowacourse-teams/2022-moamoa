import { memo } from 'react';

import styled from '@emotion/styled';

import type { Study } from '@custom-types';

import { applyHoverTransitionStyle } from '@styles/theme';

import Card from '@components/card/Card';
import Image, { ImageProps } from '@components/image/Image';
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
    <Self>
      <Card custom={{ width: '280px' }}>
        <CardImage alt={thumbnailAlt} src={thumbnailUrl} />
        <div>
          <Card.Heading custom={{ marginBottom: '8px' }}>{title}</Card.Heading>
          <Card.Content>{excerpt}</Card.Content>
          <Card.Content align="right" maxLine={1}>
            {tags && tags.map(tag => <Tag key={tag.id}>#{tag.name}</Tag>)}
          </Card.Content>
        </div>
        <StudyChipContainer>
          <StudyChip isOpen={isOpen} />
        </StudyChipContainer>
      </Card>
    </Self>
  );
};

const Self = styled.div`
  position: relative;
  height: 280px;

  ${applyHoverTransitionStyle()}
`;

type CardImageProps = Pick<ImageProps, 'alt' | 'src'>;

const CardImage = ({ alt, src }: CardImageProps) => (
  <Image custom={{ marginBottom: '16px' }} shape="rectangular" alt={alt} src={src} />
);

const Tag = styled.span`
  margin-bottom: 8px;
`;

const StudyChipContainer = styled.div`
  position: absolute;
  top: 8px;
  right: 8px;
`;

export default memo(StudyCard);
