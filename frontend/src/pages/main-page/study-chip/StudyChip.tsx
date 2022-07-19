import Chip from '@components/chip/Chip';

const StudyChip = ({ isOpen }: { isOpen: boolean }) => {
  return <Chip disabled={!isOpen}>{isOpen ? '모집중' : '모집완료'}</Chip>;
};

export default StudyChip;
