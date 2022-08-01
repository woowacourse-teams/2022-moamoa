import { theme } from '@styles/theme';

import * as S from '@main-page/components/create-new-study-button/CreateNewStudyButton.style';

type CreateNewStudyButtonProps = {
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const CreateNewStudyButton = ({ onClick }: CreateNewStudyButtonProps) => {
  return (
    <S.CreateNewStudyButton onClick={onClick} aria-label="스터디 개설 페이지 이동">
      <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path
          d="M5.325 43.5H13.81L44.923 12.387L36.437 3.902L5.325 35.015V43.5Z"
          stroke={theme.colors.white}
          strokeWidth="4"
          strokeLinejoin="round"
        />
        <path
          d="M27.952 12.387L36.437 20.872"
          stroke={theme.colors.white}
          strokeWidth="4"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
      </svg>
    </S.CreateNewStudyButton>
  );
};

export default CreateNewStudyButton;
