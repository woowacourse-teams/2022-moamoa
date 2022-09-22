import { IconButton } from '@components/button';
import { LeftDirectionIcon, RightDirectionIcon } from '@components/icons';

export type FilterSlideButtonProps = {
  direction: 'right' | 'left';
  ariaLabel: string;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};

const FilterSlideButton: React.FC<FilterSlideButtonProps> = ({ direction, ariaLabel, onClick: handleClick }) => {
  return (
    <IconButton ariaLabel={ariaLabel} onClick={handleClick} width="25px" height="25px" variant="secondary">
      {direction === 'right' ? <RightDirectionIcon /> : <LeftDirectionIcon />}
    </IconButton>
  );
};

export default FilterSlideButton;
