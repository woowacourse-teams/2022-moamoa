import { BsChevronLeft, BsChevronRight } from 'react-icons/bs';

import * as S from '@components/arrow-button/ArrowButton.style';

export interface SlideButtonProps {
  direction: 'right' | 'left';
  ariaLabel: string;
  handleSlideButtonClick: React.MouseEventHandler<HTMLButtonElement>;
}

const SlideButton: React.FC<SlideButtonProps> = ({ direction, ariaLabel, handleSlideButtonClick }) => {
  return (
    <S.Button type="button" aria-label={ariaLabel} onClick={handleSlideButtonClick}>
      {direction === 'right' ? <BsChevronRight /> : <BsChevronLeft />}
    </S.Button>
  );
};

export default SlideButton;
