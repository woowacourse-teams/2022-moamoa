import { IconButton } from '@design/components/button';
import { LeftDirectionIcon, RightDirectionIcon } from '@design/icons';

export type SlideButtonProps = {
  direction: 'right' | 'left';
  ariaLabel: string;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};

const SlideButton: React.FC<SlideButtonProps> = ({ direction, ariaLabel, onClick: handleClick }) => {
  return (
    <IconButton ariaLabel={ariaLabel} onClick={handleClick} width="25px" height="25px" variant="secondary">
      {direction === 'right' ? <RightDirectionIcon /> : <LeftDirectionIcon />}
    </IconButton>
  );
};

export default SlideButton;
