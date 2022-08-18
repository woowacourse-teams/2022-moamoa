import * as S from '@components/arrow-button/ArrowButton.style';
import { LeftDirectionSvg, RightDirectionSvg } from '@components/svg';

export type SlideButtonProps = {
  direction: 'right' | 'left';
  ariaLabel: string;
  onSlideButtonClick: React.MouseEventHandler<HTMLButtonElement>;
};

const SlideButton: React.FC<SlideButtonProps> = ({
  direction,
  ariaLabel,
  onSlideButtonClick: handleSlideButtonClick,
}) => {
  return (
    <S.Button type="button" aria-label={ariaLabel} onClick={handleSlideButtonClick}>
      {direction === 'right' ? <RightDirectionSvg /> : <LeftDirectionSvg />}
    </S.Button>
  );
};

export default SlideButton;
