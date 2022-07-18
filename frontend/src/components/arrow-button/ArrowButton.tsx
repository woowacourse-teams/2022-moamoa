import { BsChevronLeft, BsChevronRight } from 'react-icons/bs';

import * as S from '@components/arrow-button/ArrowButton.style';

export interface SlideButtonProps {
  direction: 'right' | 'left';
  handleSlideButtonClick: React.MouseEventHandler<HTMLButtonElement>;
}

const SlideButton: React.FC<SlideButtonProps> = ({ direction, handleSlideButtonClick }) => {
  return (
    <S.Button type="button" onClick={handleSlideButtonClick}>
      {direction === 'right' ? <BsChevronRight /> : <BsChevronLeft />}
    </S.Button>
  );
};

export default SlideButton;
