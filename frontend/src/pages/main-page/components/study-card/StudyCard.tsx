import { memo } from 'react';

import Card from '@components/card/Card';
import type { CardProps } from '@components/card/Card';
import StudyChip from '@components/study-chip/StudyChip';

export type StudyCardProps = {
  isOpen: boolean;
} & Omit<CardProps, 'extraChips'>;

const StudyCard: React.FC<StudyCardProps> = ({ thumbnailUrl, thumbnailAlt, title, excerpt, isOpen }) => {
  return (
    <Card
      thumbnailUrl={thumbnailUrl}
      thumbnailAlt={thumbnailAlt}
      title={title}
      excerpt={excerpt}
      extraChips={[<StudyChip key={0} isOpen={isOpen} />]}
    />
  );
};

export default memo(StudyCard);
