import Chip from '@components/chip/Chip';

type StudyChipProps = {
  className?: string;
  isOpen: boolean;
};

const StudyChip = ({ className, isOpen }: StudyChipProps) => {
  return (
    <Chip className={className} disabled={!isOpen}>
      {isOpen ? '모집중' : '모집완료'}
    </Chip>
  );
};

export default StudyChip;
