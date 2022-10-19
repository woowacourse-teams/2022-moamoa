import { memo } from 'react';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { Study } from '@custom-types';

import { applyHoverTranslateTransitionStyle } from '@styles/theme';

import Card from '@shared/card/Card';
import Flex from '@shared/flex/Flex';
import Image, { type ImageProps } from '@shared/image/Image';

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
          <Card.Content>
            <Exceprt content={excerpt} />
            <TagList tags={tags ?? []} />
          </Card.Content>
        </div>
        <StudyChipContainer>
          <StudyChip isOpen={isOpen} />
        </StudyChipContainer>
      </Card>
    </Self>
  );
};

export default memo(StudyCard);

const Self = styled.div`
  position: relative;
  height: 280px;

  ${applyHoverTranslateTransitionStyle()}
`;

type ExceprtProps = {
  content: string;
};
const Exceprt: React.FC<ExceprtProps> = ({ content }) => (
  <p
    css={css`
      margin-bottom: 20px;
    `}
  >
    {content}
  </p>
);

type TagListProps = {
  tags: StudyCardProps['tags'];
};
const TagList: React.FC<TagListProps> = ({ tags }) => (
  <Flex flexWrap="wrap" columnGap="6px" justifyContent="flex-end">
    {tags.map(tag => (
      <Tag key={tag.id}>#{tag.name}</Tag>
    ))}
  </Flex>
);

type CardImageProps = Pick<ImageProps, 'alt' | 'src'>;

const CardImage = ({ alt, src }: CardImageProps) => (
  <Image custom={{ marginBottom: '16px' }} shape="rectangular" alt={alt} src={src} />
);

const Tag = styled.span`
  margin-bottom: 4px;
`;

const StudyChipContainer = styled.div`
  position: absolute;
  top: 8px;
  right: 8px;
`;
