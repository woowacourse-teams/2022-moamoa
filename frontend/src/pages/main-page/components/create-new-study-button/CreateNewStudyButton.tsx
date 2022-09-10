import tw from '@utils/tw';

import { IconButton } from '@design/components/button';
import { PencilIcon } from '@design/icons';

export type CreateNewStudyButtonProps = {
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const CreateNewStudyButton = ({ onClick: handleClick }: CreateNewStudyButtonProps) => {
  return (
    <div css={tw`fixed right-60 bottom-50 z-3`}>
      <IconButton onClick={handleClick} ariaLabel="스터디 개설 페이지 이동" width="70px" height="70px">
        <PencilIcon />
      </IconButton>
    </div>
  );
};

export default CreateNewStudyButton;
