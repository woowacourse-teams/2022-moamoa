import { MakeOptional } from '@custom-types/index';

import { StyledChip } from './style';

export interface ChipProps {
  children: string;
  disabled: boolean;
}

export type OptionalChipProps = MakeOptional<ChipProps, 'disabled'>;

const Chip: React.FC<OptionalChipProps> = ({ children, disabled = false }) => {
  return <StyledChip disabled={disabled}>{children}</StyledChip>;
};

export default Chip;
