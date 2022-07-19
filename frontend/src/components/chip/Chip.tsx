import { MakeOptional } from '@custom-types/index';

import * as S from '@components/chip/Chip.style';

export interface ChipProps {
  className?: string;
  children: string;
  disabled: boolean;
}

export type OptionalChipProps = MakeOptional<ChipProps, 'disabled'>;

const Chip: React.FC<OptionalChipProps> = ({ className, children, disabled = false }) => {
  return (
    <S.Chip className={className} disabled={disabled}>
      {children}
    </S.Chip>
  );
};

export default Chip;
