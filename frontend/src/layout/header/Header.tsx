import { useContext, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import { PATH, PROFILE_IMAGE_URL } from '@constants';

import { useAuth } from '@hooks/useAuth';
import { useUserInfo } from '@hooks/useUserInfo';

import { SearchContext } from '@context/search/SearchProvider';

import * as S from '@layout/header/Header.style';
import DropDownBox from '@layout/header/components/drop-down-box/DropDownBox';
import Logo from '@layout/header/components/logo/Logo';
import NavButton from '@layout/header/components/nav-button/NavButton';
import SearchBar from '@layout/header/components/search-bar/SearchBar';

import Avatar from '@components/avatar/Avatar';

export type HeaderProps = {
  className?: string;
};

const MdOutlineLogin = () => (
  <svg
    stroke="currentColor"
    fill="currentColor"
    strokeWidth="0"
    viewBox="0 0 24 24"
    height="20"
    width="20"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path fill="none" d="M0 0h24v24H0z"></path>
    <path d="M11 7L9.6 8.4l2.6 2.6H2v2h10.2l-2.6 2.6L11 17l5-5-5-5zm9 12h-8v2h8c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2h-8v2h8v14z"></path>
  </svg>
);

const MdOutlineLogout = () => (
  <svg
    stroke="currentColor"
    fill="currentColor"
    strokeWidth="0"
    viewBox="0 0 24 24"
    height="20"
    width="20"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path fill="none" d="M0 0h24v24H0V0z"></path>
    <path d="M17 8l-1.41 1.41L17.17 11H9v2h8.17l-1.58 1.58L17 16l4-4-4-4zM5 5h7V3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h7v-2H5V5z"></path>
  </svg>
);

const BiBookmark = () => (
  <svg
    stroke="currentColor"
    fill="currentColor"
    strokeWidth="0"
    viewBox="0 0 24 24"
    height="20"
    width="20"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path d="M18 2H6c-1.103 0-2 .897-2 2v18l8-4.572L20 22V4c0-1.103-.897-2-2-2zm0 16.553-6-3.428-6 3.428V4h12v14.553z"></path>
  </svg>
);

const Header: React.FC<HeaderProps> = ({ className }) => {
  const { setKeyword } = useContext(SearchContext);

  const [openDropDownBox, setOpenDropDownBox] = useState(false);

  const navigate = useNavigate();

  const { logout, isLoggedIn } = useAuth();
  const { userInfo } = useUserInfo();

  const handleKeywordSubmit = (e: React.FormEvent<HTMLFormElement>, inputName: string) => {
    e.preventDefault();
    const value = (e.target as any)[inputName].value;
    if (value.length === 0) {
      alert('검색어를 입력해주세요');
      return;
    }
    setKeyword(value);
    navigate(PATH.MAIN);
  };

  const handleAvatarButtonClick = () => setOpenDropDownBox(prev => !prev);
  const handleOutsideDropBoxClick = () => setOpenDropDownBox(false);

  return (
    <S.Row className={className}>
      <a href={PATH.MAIN}>
        <Logo />
      </a>
      <S.SearchBarContainer>
        <SearchBar onSubmit={handleKeywordSubmit} />
      </S.SearchBarContainer>
      {isLoggedIn ? (
        <S.Nav>
          <Link to={PATH.MY_STUDY}>
            <NavButton ariaLabel="내 스터디">
              <BiBookmark />
              <span>내 스터디</span>
            </NavButton>
          </Link>
          <S.AvatarButton onClick={handleAvatarButtonClick}>
            <Avatar profileImg={userInfo.imageUrl} profileAlt={`${userInfo.username} 이미지`} />
          </S.AvatarButton>
          {openDropDownBox && (
            <DropDownBox top={'70px'} right={'50px'} onOutOfBoxClick={handleOutsideDropBoxClick}>
              <NavButton onClick={logout} ariaLabel="로그아웃">
                <MdOutlineLogout />
                <span>로그아웃</span>
              </NavButton>
            </DropDownBox>
          )}
        </S.Nav>
      ) : (
        <a href={`https://github.com/login/oauth/authorize?client_id=${process.env.CLIENT_ID}`}>
          <NavButton ariaLabel="로그인">
            <MdOutlineLogin />
            <span>로그인</span>
          </NavButton>
        </a>
      )}
    </S.Row>
  );
};

export default Header;
