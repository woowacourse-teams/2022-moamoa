import { useContext, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import tw from '@utils/tw';

import { useAuth } from '@hooks/useAuth';
import { useUserInfo } from '@hooks/useUserInfo';

import { SearchContext } from '@context/search/SearchProvider';

import * as S from '@layout/header/Header.style';
import Logo from '@layout/header/components/logo/Logo';
import NavButton from '@layout/header/components/nav-button/NavButton';
import SearchBar from '@layout/header/components/search-bar/SearchBar';

import Avatar from '@design/components/avatar/Avatar';
import { IconButton } from '@design/components/button';
import DropDownBox from '@design/components/drop-down-box/DropDownBox';
import Flex from '@design/components/flex/Flex';
import { BookmarkIcon, LoginIcon, LogoutIcon } from '@design/icons';

const Header: React.FC = () => {
  const { setKeyword } = useContext(SearchContext);

  const [isOpenDropDownBox, setIsOpenDropDownBox] = useState(false);

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

  const handleLogoutButtonClick = () => {
    logout();
  };

  const handleAvatarButtonClick = () => {
    setIsOpenDropDownBox(prev => !prev);
  };
  const handleDropDownBoxClose = () => setIsOpenDropDownBox(false);

  return (
    <S.Header>
      <a href={PATH.MAIN}>
        <Logo />
      </a>
      <S.SearchBarContainer>
        <SearchBar onSubmit={handleKeywordSubmit} />
      </S.SearchBarContainer>
      {isLoggedIn ? (
        <nav>
          <Flex gap="16px">
            <Link to={PATH.MY_STUDY}>
              <NavButton ariaLabel="내 스터디">
                <BookmarkIcon />
                <span>내 스터디</span>
              </NavButton>
            </Link>
            <div css={tw`relative`}>
              <IconButton
                onClick={handleAvatarButtonClick}
                width="38px"
                height="38px"
                ariaLabel={userInfo.username}
                variant="secondary"
              >
                <Avatar src={userInfo.imageUrl} name={userInfo.username} />
              </IconButton>
              {isOpenDropDownBox && (
                <DropDownBox top="40px" right={0} onClose={handleDropDownBoxClose} padding="16px">
                  <NavButton onClick={handleLogoutButtonClick} ariaLabel="로그아웃">
                    <LogoutIcon />
                    <span>로그아웃</span>
                  </NavButton>
                </DropDownBox>
              )}
            </div>
          </Flex>
        </nav>
      ) : (
        <a href={`https://github.com/login/oauth/authorize?client_id=${process.env.CLIENT_ID}`}>
          <NavButton ariaLabel="로그인">
            <LoginIcon />
            <span>로그인</span>
          </NavButton>
        </a>
      )}
    </S.Header>
  );
};

export default Header;
