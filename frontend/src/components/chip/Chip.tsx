import { MakeOptional } from '@custom-types';

import * as S from '@components/chip/Chip.style';

export interface ChipProps {
  children: string;
  disabled: boolean;
}

export type OptionalChipProps = MakeOptional<ChipProps, 'disabled'>;

const Chip: React.FC<OptionalChipProps> = ({ children, disabled = false }) => {
  return <S.Chip disabled={disabled}>{children}</S.Chip>;
};

export default Chip;
