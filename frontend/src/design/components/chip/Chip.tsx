import * as S from '@design/components/chip/Chip.style';

export type ChipProps = {
  children: string;
  variant?: 'primary' | 'secondary';
};

const Chip: React.FC<ChipProps> = ({ children, variant = 'primary' }) => {
  return <S.Chip variant={variant}>{children}</S.Chip>;
};

export default Chip;
