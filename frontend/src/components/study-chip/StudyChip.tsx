import Chip from '@components/chip/Chip';

export type StudyChipProps = {
  isOpen: boolean;
};

const StudyChip = ({ isOpen }: StudyChipProps) => {
  return <Chip variant={isOpen ? 'primary' : 'secondary'}>{isOpen ? '모집중' : '모집완료'}</Chip>;
};

export default StudyChip;
