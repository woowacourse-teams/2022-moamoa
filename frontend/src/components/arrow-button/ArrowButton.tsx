import * as S from '@components/arrow-button/ArrowButton.style';

export type SlideButtonProps = {
  direction: 'right' | 'left';
  ariaLabel: string;
  onSlideButtonClick: React.MouseEventHandler<HTMLButtonElement>;
};

const BsChevronLeft = () => (
  <svg
    stroke="currentColor"
    fill="currentColor"
    strokeWidth="0"
    viewBox="0 0 16 16"
    height="1em"
    width="1em"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      fillRule="evenodd"
      d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"
    ></path>
  </svg>
);

const BsChevronRight = () => (
  <svg
    stroke="currentColor"
    fill="currentColor"
    strokeWidth="0"
    viewBox="0 0 16 16"
    height="1em"
    width="1em"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      fillRule="evenodd"
      d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z"
    ></path>
  </svg>
);

const SlideButton: React.FC<SlideButtonProps> = ({
  direction,
  ariaLabel,
  onSlideButtonClick: handleSlideButtonClick,
}) => {
  return (
    <S.Button type="button" aria-label={ariaLabel} onClick={handleSlideButtonClick}>
      {direction === 'right' ? <BsChevronRight /> : <BsChevronLeft />}
    </S.Button>
  );
};

export default SlideButton;
