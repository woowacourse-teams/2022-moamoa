import { noop } from '@utils/index';

import * as S from '@layout/header/components/nav-button/NavButton.style';

export type NavButtonProps = {
  children: React.ReactNode;
  ariaLabel: string;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

const NavButton: React.FC<NavButtonProps> = ({ children, ariaLabel, onClick: handleClick = noop }) => {
  return (
    <S.NavButton aria-label={ariaLabel} onClick={handleClick}>
      {children}
    </S.NavButton>
  );
};

export default NavButton;
