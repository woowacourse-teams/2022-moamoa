import { BsChevronLeft, BsChevronRight } from 'react-icons/bs';

import * as S from './SlideButton.style';

export interface SlideButtonProps {
  rightDirection: boolean;
  handleSlideButtonClick: React.MouseEventHandler<HTMLButtonElement>;
}

const SlideButton: React.FC<SlideButtonProps> = ({ rightDirection, handleSlideButtonClick }) => {
  return (
    <S.Button type="button" onClick={handleSlideButtonClick}>
      {rightDirection ? <BsChevronRight /> : <BsChevronLeft />}
    </S.Button>
  );
};

export default SlideButton;
