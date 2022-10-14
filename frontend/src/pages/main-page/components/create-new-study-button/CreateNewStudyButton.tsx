import styled from '@emotion/styled';

import { IconButton } from '@shared/button';
import { PencilIcon } from '@shared/icons';

export type CreateNewStudyButtonProps = {
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const CreateNewStudyButton = ({ onClick: handleClick }: CreateNewStudyButtonProps) => {
  return (
    <Self>
      <IconButton onClick={handleClick} ariaLabel="스터디 개설 페이지 이동" custom={{ width: '70px', height: '70px' }}>
        <PencilIcon />
      </IconButton>
    </Self>
  );
};

const Self = styled.div`
  position: fixed;
  right: 60px;
  bottom: 50px;
  z-index: 3;
`;

export default CreateNewStudyButton;
