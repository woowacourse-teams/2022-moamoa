import type { Member } from '@custom-types';

import Avatar from '@shared/avatar/Avatar';
import { IconButton } from '@shared/button';

type AvatarButtonProps = {
  userInfo: Member;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const AvatarButton: React.FC<AvatarButtonProps> = ({ userInfo, onClick: handleClick }) => (
  <IconButton
    onClick={handleClick}
    ariaLabel={userInfo.username}
    variant="secondary"
    custom={{ width: '38px', height: '38px' }}
  >
    <Avatar src={userInfo.imageUrl} name={userInfo.username} />
  </IconButton>
);

export default AvatarButton;
