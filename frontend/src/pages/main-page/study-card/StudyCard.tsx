import { memo } from 'react';

import StudyChip from '@pages/main-page/study-chip/StudyChip';

import Card from '@components/card/Card';
import type { CardProps } from '@components/card/Card';

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
