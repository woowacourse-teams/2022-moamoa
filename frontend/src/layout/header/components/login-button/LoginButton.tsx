import NavButton from '@layout/header/components/nav-button/NavButton';

import { LoginIcon } from '@components/@shared/icons';

const LoginButton = () => (
  <a href={`https://github.com/login/oauth/authorize?client_id=${process.env.CLIENT_ID}`}>
    <NavButton ariaLabel="로그인">
      <LoginIcon />
      <span>Github 로그인</span>
    </NavButton>
  </a>
);

export default LoginButton;
