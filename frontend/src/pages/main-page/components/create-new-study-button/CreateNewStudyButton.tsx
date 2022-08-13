import { theme } from '@styles/theme';

import { PencilSvg } from '@components/svg';

import * as S from '@main-page/components/create-new-study-button/CreateNewStudyButton.style';

type CreateNewStudyButtonProps = {
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const CreateNewStudyButton = ({ onClick: handleClick }: CreateNewStudyButtonProps) => {
  return (
    <S.CreateNewStudyButton onClick={handleClick} aria-label="스터디 개설 페이지 이동">
      <PencilSvg />
    </S.CreateNewStudyButton>
  );
};

export default CreateNewStudyButton;
