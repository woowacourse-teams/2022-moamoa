import { useContext, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { PATH } from '@constants';

import { Member } from '@custom-types';

import { mqDown } from '@styles/responsive';

import { useAuth } from '@hooks/useAuth';
import { useUserInfo } from '@hooks/useUserInfo';

import { SearchContext } from '@context/search/SearchProvider';

import Logo from '@layout/header/components/logo/Logo';
import NavButton from '@layout/header/components/nav-button/NavButton';
import SearchBar from '@layout/header/components/search-bar/SearchBar';

import Avatar from '@components/avatar/Avatar';
import { IconButton } from '@components/button';
import DropDownBox from '@components/drop-down-box/DropDownBox';
import Flex from '@components/flex/Flex';
import { BookmarkIcon, LoginIcon, LogoutIcon } from '@components/icons';

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
    <Self>
      <LogoLink />
      <SearchBarContainer>
        <SearchBar onSubmit={handleKeywordSubmit} />
      </SearchBarContainer>
      {isLoggedIn ? (
        <nav>
          <Flex columnGap="16px" flexWrap="nowrap">
            <GoToMyStudyPageLink />
            <div
              css={css`
                position: relative;
              `}
            >
              <AvatarButton userInfo={userInfo} onClick={handleAvatarButtonClick} />
              <DropDownBox
                isOpen={isOpenDropDownBox}
                top="40px"
                right={0}
                onClose={handleDropDownBoxClose}
                custom={{ padding: '16px' }}
              >
                <NavButton onClick={handleLogoutButtonClick} ariaLabel="로그아웃">
                  <LogoutIcon />
                  <span>로그아웃</span>
                </NavButton>
              </DropDownBox>
            </div>
          </Flex>
        </nav>
      ) : (
        <LoginButton />
      )}
    </Self>
  );
};

export const Self = styled.header`
  ${({ theme }) => css`
    display: flex;
    justify-content: space-between;
    align-items: center;
    column-gap: 20px;

    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 5;

    padding: 20px 40px;

    background-color: ${theme.colors.secondary.light};
    border-bottom: 1px solid ${theme.colors.secondary.dark};

    ${mqDown('md')} {
      padding: 16px 24px;
    }

    ${mqDown('sm')} {
      padding: 10px 12px;
    }
  `}
`;

const LogoLink = () => (
  <a href={PATH.MAIN}>
    <Logo />
  </a>
);

const GoToMyStudyPageLink = () => (
  <Link to={PATH.MY_STUDY}>
    <NavButton ariaLabel="내 스터디">
      <BookmarkIcon />
      <span>내 스터디</span>
    </NavButton>
  </Link>
);

const SearchBarContainer = styled.div`
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  width: 100%;
  max-width: 400px;

  ${mqDown('lg')} {
    position: static;
    left: 0;
    top: 0;
    transform: none;
  }
`;

const LoginButton = () => (
  <a href={`https://github.com/login/oauth/authorize?client_id=${process.env.CLIENT_ID}`}>
    <NavButton ariaLabel="로그인">
      <LoginIcon />
      <span>Github 로그인</span>
    </NavButton>
  </a>
);

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

export default Header;
