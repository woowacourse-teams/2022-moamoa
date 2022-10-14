import NavButton from '@layout/header/components/nav-button/NavButton';

import DropDownBox from '@shared/drop-down-box/DropDownBox';

import { LogoutIcon } from '@components/@shared/icons';

type UserDropdownProps = {
  isOpen: boolean;
  onClose: () => void;
  onLogoutButtonClick: () => void;
};
const UserDropdown: React.FC<UserDropdownProps> = ({
  isOpen,
  onClose: handleClose,
  onLogoutButtonClick: handleLogoutButtonClick,
}) => (
  <DropDownBox isOpen={isOpen} top="40px" right={0} onClose={handleClose} custom={{ padding: '16px' }}>
    <NavButton onClick={handleLogoutButtonClick} ariaLabel="로그아웃">
      <LogoutIcon />
      <span>로그아웃</span>
    </NavButton>
  </DropDownBox>
);

export default UserDropdown;
