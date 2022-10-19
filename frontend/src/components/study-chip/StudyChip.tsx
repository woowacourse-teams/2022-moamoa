import Chip from '@shared/chip/Chip';

export type StudyChipProps = {
  isOpen: boolean;
};

const StudyChip = ({ isOpen }: StudyChipProps) => {
  return isOpen ? <Chip variant="primary">모집중</Chip> : <Chip variant="secondary">모집완료</Chip>;
};

export default StudyChip;
