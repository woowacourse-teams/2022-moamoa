import Card from '@components/Card';
import type { CardProps } from '@components/Card';
import StudyChip from '@components/StudyChip';

export type StudyCardProps = {
  isOpen: boolean;
} & Omit<CardProps, 'extraChips'>;

const StudyCard: React.FC<StudyCardProps> = ({ thumbnailUrl, thumbnailAlt, title, description, isOpen }) => {
  return (
    <Card
      thumbnailUrl={thumbnailUrl}
      thumbnailAlt={thumbnailAlt}
      title={title}
      description={description}
      extraChips={[<StudyChip isOpen={isOpen} />]}
    />
  );
};

export default StudyCard;
