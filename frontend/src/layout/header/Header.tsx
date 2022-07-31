import { useContext, useState } from 'react';
import { BiBookmark } from 'react-icons/bi';
import { MdOutlineLogin, MdOutlineLogout } from 'react-icons/md';
import { Link, useNavigate } from 'react-router-dom';

import { PATH, PROFILE_IMAGE_URL } from '@constants';

import { useAuth } from '@hooks/useAuth';

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

const Header: React.FC<HeaderProps> = ({ className }) => {
  const { setKeyword } = useContext(SearchContext);

  const [openDropDownBox, setOpenDropDownBox] = useState(false);

  const navigate = useNavigate();

  const { logout, isLoggedIn } = useAuth();

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
      <Link to={PATH.MAIN}>
        <Logo />
      </Link>
      <S.SearchBarContainer>
        <SearchBar onSubmit={handleKeywordSubmit} />
      </S.SearchBarContainer>
      {isLoggedIn ? (
        <S.Nav>
          <Link to={PATH.MY_STUDY}>
            <NavButton ariaLabel="내 스터디">
              <BiBookmark size={20} />
              <span>내 스터디</span>
            </NavButton>
          </Link>
          <S.AvatarButton onClick={handleAvatarButtonClick}>
            <Avatar
              // TODO: Context에서 정보를 가져온다
              profileImg={PROFILE_IMAGE_URL}
              profileAlt="프로필 이미지"
            />
          </S.AvatarButton>
          {openDropDownBox && (
            <DropDownBox top={'70px'} right={'50px'} onOutOfBoxClick={handleOutsideDropBoxClick}>
              <NavButton onClick={logout} ariaLabel="로그아웃">
                <MdOutlineLogout size={20} />
                <span>로그아웃</span>
              </NavButton>
            </DropDownBox>
          )}
        </S.Nav>
      ) : (
        <a href={`https://github.com/login/oauth/authorize?client_id=${process.env.CLIENT_ID}`}>
          <NavButton ariaLabel="로그인">
            <MdOutlineLogin size={20} />
            <span>로그인</span>
          </NavButton>
        </a>
      )}
    </S.Row>
  );
};

export default Header;
