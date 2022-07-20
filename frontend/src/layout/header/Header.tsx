import { useContext } from 'react';
import { MdOutlineLogin, MdOutlineLogout } from 'react-icons/md';

import { useAuth } from '@hooks/useAuth';

import { LoginContext } from '@context/login/LoginProvider';
import { SearchContext } from '@context/search/SearchProvider';

import * as S from '@layout/header/Header.style';
import Logo from '@layout/header/logo/Logo';
import SearchBar from '@layout/header/search-bar/SearchBar';

import Avatar from '@components/avatar/Avatar';

type HeaderProps = {
  className?: string;
};

const Header: React.FC<HeaderProps> = ({ className }) => {
  const { isLoggedIn } = useContext(LoginContext);
  const { setKeyword } = useContext(SearchContext);

  const { logout } = useAuth();

  const handleKeywordSubmit = (e: React.FormEvent<HTMLFormElement>, inputName: string) => {
    e.preventDefault();
    const value = (e.target as any)[inputName].value;
    if (value.length === 0) {
      alert('검색어를 입력해주세요');
      return;
    }
    setKeyword(value);
  };

  return (
    <S.Row className={className}>
      <a href="/">
        <Logo />
      </a>
      <S.SearchBarContainer>
        <SearchBar onSubmit={handleKeywordSubmit} />
      </S.SearchBarContainer>
      {isLoggedIn ? (
        <S.Nav>
          <S.NavButton onClick={logout}>
            <MdOutlineLogout />
            <span>로그아웃</span>
          </S.NavButton>
          <Avatar
            // TODO: Context에서 정보를 가져온다
            profileImg="https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80"
            profileAlt="프로필 이미지"
          />
        </S.Nav>
      ) : (
        <a href={`https://github.com/login/oauth/authorize?client_id=${process.env.CLIENT_ID}`}>
          <S.NavButton>
            <MdOutlineLogin />
            <span>로그인</span>
          </S.NavButton>
        </a>
      )}
    </S.Row>
  );
};

export default Header;